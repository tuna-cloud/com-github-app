package com.github.app.api.handler.open;

import com.github.app.api.services.UserService;
import com.github.app.api.handler.UriHandler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.KeyStoreOptions;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.auth.jwt.JWTAuthOptions;
import io.vertx.ext.auth.jwt.JWTOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class JWTIssueHandler implements UriHandler {
    private JWTAuthOptions config;

    @Autowired
    private UserService userService;

    @Override
    public void registeUriHandler(Router router) {
        router.post().path("/auth").produces("application/json;charset=UTF-8").blockingHandler(this::issueJWTToken, false);
    }

    public void issueJWTToken(RoutingContext routingContext) {

        JsonObject jsonObject = new JsonObject(routingContext.getBodyAsString());

        String account = jsonObject.getString("account");
        String password = jsonObject.getString("password");

        if (StringUtils.isEmpty(account)) {
            responseFailure(routingContext, "account is missing");
            return;
        }
        if (StringUtils.isEmpty(password)) {
            responseFailure(routingContext, "password is missing");
            return;
        }

        if (config == null) {
            JsonObject sysConfig = routingContext.vertx().getOrCreateContext().config().getJsonObject("jwt.keystore");
            config = new JWTAuthOptions()
                    .setKeyStore(new KeyStoreOptions()
                            .setPath(sysConfig.getString("path"))
                            .setPassword(sysConfig.getString("password")));
        }

        if (userService.auth(account, password)) {
            JWTAuth provider = JWTAuth.create(routingContext.vertx(), config);
            String token = provider.generateToken(new JsonObject().put("account", account),
                    new JWTOptions().setExpiresInMinutes(60 * 3L));
            responseSuccess(routingContext, "", token);
        } else {
            responseFailure(routingContext, "auth failed, account or password error");
        }
    }
}
