package com.github.app.api.handler.api;

import com.github.app.api.bll.UserService;
import com.github.app.api.handler.UriHandler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserHandler implements UriHandler {

    @Autowired
    private UserService userService;

    @Override
    public void registeUriHandler(Router router) {
        router.post().path("/users").produces("application/json;charset=UTF-8").blockingHandler(this::add, false);
        router.delete().path("/users/:number").produces("application/json;charset=UTF-8").blockingHandler(this::delete, false);
        router.put().path("/users/:number").produces("application/json;charset=UTF-8").blockingHandler(this::edit, false);
        router.get().path("/users").produces("application/json;charset=UTF-8").blockingHandler(this::query, false);
        router.get().path("/users/:number").produces("application/json;charset=UTF-8").blockingHandler(this::queryOne, false);
        router.get().path("/users/current/login").produces("application/json;charset=UTF-8").blockingHandler(this::currentUserInfo, false);
    }

    public void add(RoutingContext routingContext) {
        responseSuccess(routingContext, "test");
    }

    public void delete(RoutingContext routingContext) {
        responseSuccess(routingContext, "test");
    }

    public void edit(RoutingContext routingContext) {
        responseSuccess(routingContext, "test");
    }

    public void query(RoutingContext routingContext) {
        responseSuccess(routingContext, "test");
    }

    public void queryOne(RoutingContext routingContext) {
        responseSuccess(routingContext, "test");
    }

    public void currentUserInfo(RoutingContext routingContext) {
        try {
            JsonObject user = userService.selectUserInfoByAccount(routingContext.get("account"));
            responseSuccess(routingContext, user);
        } catch (Exception e) {
            responseFailure(routingContext, e.getLocalizedMessage());
        }
    }

}
