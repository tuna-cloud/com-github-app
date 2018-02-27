package com.github.app.api.handler.api;

import com.github.app.api.dao.domain.Popedom;
import com.github.app.api.handler.UriHandler;
import com.github.app.api.services.SystemOperationService;
import com.github.app.api.utils.ConfigLoader;
import com.github.app.api.utils.RequestUtils;
import com.github.app.utils.ServerEnvConstant;
import io.vertx.core.MultiMap;
import io.vertx.core.http.HttpMethod;
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
        router.get().path("/sysbackup").produces(CONTENT_TYPE).blockingHandler(this::list, false);
        router.put().path("/sysbackup").produces(CONTENT_TYPE).blockingHandler(this::backup, false);
        router.put().path("/sysrestore").produces(CONTENT_TYPE).blockingHandler(this::restore, false);
        router.get().path("/sysdownload").blockingHandler(this::download, false);
    }

    @Override
    public void registePopedom(List<Popedom> list) {
        list.add(new Popedom.Builder().name("*查询备份").code("/[a-zA-Z]+/sysbackup/" + HttpMethod.GET.name()).build());
        list.add(new Popedom.Builder().name("*系统备份").code("/[a-zA-Z]+/sysbackup/" + HttpMethod.PUT.name()).build());
        list.add(new Popedom.Builder().name("*系统恢复").code("/[a-zA-Z]+/sysrestore/" + HttpMethod.PUT.name()).build());
        list.add(new Popedom.Builder().name("*下载备份").code("/[a-zA-Z]+/sysdownload/" + HttpMethod.GET.name()).build());
    }

    public void list(RoutingContext routingContext) {
        MultiMap params = routingContext.queryParams();
        JsonObject jsonObject = operationService.list(RequestUtils.getInteger(params, "offset"), RequestUtils.getInteger(params, "rows"));
        responseSuccess(routingContext, jsonObject);
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
