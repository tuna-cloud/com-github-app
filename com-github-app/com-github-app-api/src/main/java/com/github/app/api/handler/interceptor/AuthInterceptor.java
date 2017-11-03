package com.github.app.api.handler.interceptor;

import com.github.app.api.handler.UriHandler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.KeyStoreOptions;
import io.vertx.ext.auth.User;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.auth.jwt.JWTAuthOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class AuthInterceptor implements UriHandler {
    private JWTAuthOptions config;
    private static Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

    @Override
    public void registeUriHandler(Router router) {
        router.route().path("/*").handler(this::loginAuthentication);
        router.route().path("/*").handler(this::operationAuthentication);
    }

    /**
     * 身份验证拦截器
     * 验证服务调用者是否已登录系统
     * @param routingContext
     */
    public void loginAuthentication(RoutingContext routingContext) {
        String token = routingContext.request().getHeader("X-Token");

        if(StringUtils.isEmpty(token)) {
            response(routingContext, CODE_JWT_TOKEN_MISSING, "token must be filled");
            return;
        }

        routingContext.vertx().executeBlocking(future -> {

            if(config == null) {
                JsonObject sysConfig = routingContext.vertx().getOrCreateContext().config().getJsonObject("jwt.keystore");
                config = new JWTAuthOptions()
                        .setKeyStore(new KeyStoreOptions()
                                .setPath(sysConfig.getString("path"))
                                .setPassword(sysConfig.getString("password")));
            }

            JWTAuth jwtAuth = JWTAuth.create(routingContext.vertx(), config);
            jwtAuth.authenticate(new JsonObject().put("jwt", token), res -> {
                if (res.succeeded()) {
                    User theUser = res.result();
                    String account = theUser.principal().getString("account");
                    future.complete(account);
                } else {
                    future.complete();
                }
            });

        }, result -> {
            if(result.succeeded()) {
                String account = (String) result.result();
                if(StringUtils.isEmpty(account)) {
                    response(routingContext, CODE_JWT_TOKEN_INVALIDATE, "token invalidate");
                } else {
                    routingContext.put("account", account);
                    routingContext.next();
                }
            } else {
                responseFailure(routingContext, result.cause().getLocalizedMessage());
            }
        });
    }

    /**
     * 操作拦截器
     * 验证服务调用者是否有权限操作次资源
     * @param routingContext
     */
    public void operationAuthentication(RoutingContext routingContext) {
        logger.info("-->operationAuthentication");
        routingContext.next();
    }

}
