package com.github.app.api.handler.open;

import com.github.app.api.handler.UriHandler;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;


@Component
public class DownLoadFileHandler implements UriHandler {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void registeUriHandler(Router router) {
        router.get().path("/file").blockingHandler(this::sendFile, false);
        router.get().path("/block").blockingHandler(this::testBlock, false);
    }

    /**
     * @param routingContext
     */
    public void sendFile(RoutingContext routingContext) {
        routingContext.response().headers().add("content-disposition", "attachment;filename=account.tar.gz");
        routingContext.response().sendFile("/home/xsy/material/account.tar.gz");
    }

    public void testBlock(RoutingContext routingContext) {
        System.out.println(Thread.currentThread().getName() + sdf.format(new Date()));
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        response(routingContext, 0, sdf.format(new Date()) + ":" + Thread.currentThread().getName());
        System.out.println("111111->" + sdf.format(new Date()));
    }
}
