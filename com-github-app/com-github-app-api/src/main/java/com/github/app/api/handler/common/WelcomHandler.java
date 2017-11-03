package com.github.app.api.handler.common;

import com.github.app.api.handler.UriHandler;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import org.springframework.stereotype.Component;

@Component
public class WelcomHandler implements UriHandler {

    @Override
    public void registeUriHandler(Router router) {
        router.route("/").produces("application/json;charset=UTF-8").handler(this::welcome);
    }

    public void welcome(RoutingContext routingContext) {
        responseSuccess(routingContext, "welcome to iot api service, version v1.0.0");
    }
}
