package com.github.app.api.handler.open;

import com.github.app.api.handler.UriHandler;
import com.github.app.utils.MD5Utils;
import com.github.app.utils.ServerEnvConstant;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;


@Component
public class DownLoadFileHandler implements UriHandler {

    @Override
    public void registeUriHandler(Router router) {
        router.get().path("/download").blockingHandler(this::downloadFile, false);
    }

    /**
     * @param routingContext
     */
    public void downloadFile(RoutingContext routingContext) {
        List<String> list = routingContext.queryParam("fileName");
        if(list == null || list.size() < 1) {
            responseFailure(routingContext, "paremeter missing");
            return;
        }
        List<String> list1 = routingContext.queryParam("code");
        if(list1 == null || list1.size() < 1) {
            responseFailure(routingContext, "operation not permited");
            return;
        }

        if (!MD5Utils.validateMd5WithSalt(list.get(0), list1.get(0))) {
            responseFailure(routingContext, "sign is error");
            return;
        }

        String sqlFileName = System.getenv(ServerEnvConstant.APP_HOME)
                + File.separator + "data"
                + File.separator + "backup"
                + File.separator + list.get(0);

        routingContext.response().headers().add("content-disposition", "attachment;filename=" + list.get(0));
        routingContext.response().sendFile(sqlFileName);
    }

}
