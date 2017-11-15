package com.github.app.api.handler.interceptor;

import com.github.app.api.handler.UriHandler;
import com.github.app.api.utils.CmdParase;
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
    private static Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);
    private static JWTAuthOptions jwtAuthOptions;
    static {
        JsonObject sysConfig = CmdParase.getInstance().getServerCfg().getJsonObject("jwt.keystore");
        jwtAuthOptions = new JWTAuthOptions()
                .setKeyStore(new KeyStoreOptions()
                        .setPath(sysConfig.getString("path"))
                        .setPassword(sysConfig.getString("password")));
    }
    private JWTAuth jwtAuth;


    @Override
    public void registeUriHandler(Router router) {
        router.route().path("/*").blockingHandler(this::loginAuthentication, false);
        router.route().path("/*").blockingHandler(this::operationAuthentication, false);
    }

    /**
     * 身份验证拦截器
     * 验证服务调用者是否已登录系统
     *
     * @param routingContext
     */
    public void loginAuthentication(RoutingContext routingContext) {
        try {
            String token = routingContext.request().getHeader("X-Token");

            if (StringUtils.isEmpty(token)) {
                response(routingContext, CODE_JWT_TOKEN_MISSING, "token must be filled");
                return;
            }

            if(jwtAuth == null) {
                jwtAuth = JWTAuth.create(routingContext.vertx(), jwtAuthOptions);
            }

            jwtAuth.authenticate(new JsonObject().put("jwt", token), res -> {
                if (res.succeeded()) {
                    User theUser = res.result();
                    String account = theUser.principal().getString("account");
                    routingContext.put("account", account);
                    routingContext.next();
                } else {
                    response(routingContext, CODE_JWT_TOKEN_INVALIDATE, "token invalidate");
                }
            });
        } catch (Exception e) {
            responseFailure(routingContext, e.getLocalizedMessage());
        }
    }

    /**
     * 操作拦截器
     * 验证服务调用者是否有权限操作次资源
     *
     * @param routingContext
     */
    public void operationAuthentication(RoutingContext routingContext) {
        logger.info("-->operationAuthentication");
        routingContext.next();
    }

}
