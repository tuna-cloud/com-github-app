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
        router.post().path("/users").produces("application/json;charset=UTF-8").handler(this::add);
        router.delete().path("/users/:number").produces("application/json;charset=UTF-8").handler(this::delete);
        router.put().path("/users/:number").produces("application/json;charset=UTF-8").handler(this::edit);
        router.get().path("/users").produces("application/json;charset=UTF-8").handler(this::query);
        router.get().path("/users/:number").produces("application/json;charset=UTF-8").handler(this::queryOne);
        router.get().path("/users/current/login").produces("application/json;charset=UTF-8").handler(this::currentUserInfo);
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
        routingContext.vertx().executeBlocking(future -> {
            JsonObject user = userService.selectUserInfoByAccount(routingContext.get("account"));
            future.complete(user);
        }, result -> {
            if(result.succeeded()) {
                responseSuccess(routingContext, result.result());
            } else {
                responseFailure(routingContext, result.cause().getLocalizedMessage());
            }
        });
    }

}
