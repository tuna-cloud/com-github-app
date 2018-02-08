package com.github.app.api.handler.api;

import com.github.app.api.handler.UriHandler;
import io.vertx.core.MultiMap;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class LogHandler implements UriHandler{
    @Override
    public void registeUriHandler(Router router) {
        router.post().path("/log").produces("application/json;charset=UTF-8").blockingHandler(this::test, false);
        router.put().path("/log").produces("application/json;charset=UTF-8").blockingHandler(this::test, false);
        router.get().path("/log/:logId").produces("application/json;charset=UTF-8").blockingHandler(this::test, false);
        router.delete().path("/log/:logId").produces("application/json;charset=UTF-8").blockingHandler(this::test, false);
        router.get().path("/log").produces("application/json;charset=UTF-8").blockingHandler(this::test, false);
        router.get().path("/log/pwd/test").produces("application/json;charset=UTF-8").blockingHandler(this::test, false);
    }

    public void test(RoutingContext routingContext) {
        Map<String, String> pathParams = routingContext.pathParams();
        MultiMap map = routingContext.queryParams();

        responseSuccess(routingContext);
    }
}
