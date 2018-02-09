package com.github.app.api.handler.api;

import com.github.app.api.handler.UriHandler;
import com.github.app.api.services.SystemOperationService;
import com.github.app.api.utils.ConfigLoader;
import com.github.app.utils.ServerEnvConstant;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Component
public class SystemOperationHandler implements UriHandler {

    @Autowired
    private SystemOperationService operationService;

    @Override
    public void registeUriHandler(Router router) {
        router.put().path("/sysinstall").produces(CONTENT_TYPE).blockingHandler(this::install, false);
        router.put().path("/sysbackup").produces(CONTENT_TYPE).blockingHandler(this::backup, false);
        router.put().path("/sysrestore").produces(CONTENT_TYPE).blockingHandler(this::restore, false);
        router.put().path("/sysdownload").blockingHandler(this::download, false);
    }

    public void install(RoutingContext routingContext) {
        JsonObject config = ConfigLoader.getServerCfg();
        operationService.install(config);
        responseSuccess(routingContext);
    }

    public void backup(RoutingContext routingContext) {
        JsonObject config = ConfigLoader.getServerCfg();
        operationService.backup(config);
        responseSuccess(routingContext);
    }

    public void restore(RoutingContext routingContext) {
        List<String> list = routingContext.queryParam("fileName");
        if(list == null || list.size() < 1) {
            responseFailure(routingContext, "fileName must be filled");
            return;
        }

        JsonObject config = ConfigLoader.getServerCfg();

        operationService.restore(config, list.get(0));
        responseSuccess(routingContext);
    }

    public void download(RoutingContext routingContext) {
        List<String> list = routingContext.queryParam("fileName");
        if(list == null || list.size() < 1) {
            responseFailure(routingContext, "fileName must be filled");
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
