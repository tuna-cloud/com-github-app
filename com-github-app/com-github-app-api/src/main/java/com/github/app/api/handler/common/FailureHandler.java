package com.github.app.api.handler.common;

import com.github.app.api.handler.UriHandler;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

@Component
public class FailureHandler implements UriHandler {

    @Override
    public void registeUriHandler(Router router) {
        router.route("/*").produces(CONTENT_TYPE).failureHandler(this::failure);
    }

    public void failure(RoutingContext routingContext) {
        if (routingContext.failure() != null) {
            responseFailure(routingContext);
        } else {
            responseFailure(routingContext," error happen unknew");
        }
    }

    /**
     * 获取详细的异常链信息--精准定位异常位置
     *
     * @param aThrowable
     * @return
     */
    private static String getStackTrace(Throwable aThrowable) {
        Writer result = new StringWriter();
        PrintWriter printWriter = new PrintWriter(result);
        aThrowable.printStackTrace(printWriter);
        return result.toString();
    }
}
