package com.github.app.api.handler.common;

import com.github.app.api.handler.UriHandler;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import org.springframework.stereotype.Component;

@Component
public class FailureHandler implements UriHandler {

    @Override
    public void registeUriHandler(Router router) {
        router.route("/*").produces("application/json;charset=UTF-8").failureHandler(this::failure);
    }

    public void failure(RoutingContext routingContext) {
        if (routingContext.failure() != null) {
            responseFailure(routingContext, routingContext.failure().getLocalizedMessage());
        } else {
            responseFailure(routingContext," error happen unknew");
        }
    }
}
