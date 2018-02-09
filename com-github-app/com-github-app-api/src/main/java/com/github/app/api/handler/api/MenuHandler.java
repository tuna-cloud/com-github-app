package com.github.app.api.handler.api;

import com.github.app.api.dao.domain.Menu;
import com.github.app.api.handler.UriHandler;
import com.github.app.api.services.MenuService;
import com.github.app.api.utils.RequestUtils;
import io.vertx.core.MultiMap;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MenuHandler implements UriHandler{

    @Autowired
    private MenuService menuService;

    @Override
    public void registeUriHandler(Router router) {
        router.post().path("/menu").produces(CONTENT_TYPE).blockingHandler(this::saveOrUpdate, false);
        router.delete().path("/menu/:menuId").produces(CONTENT_TYPE).blockingHandler(this::delete, false);
        router.put().path("/menu").produces(CONTENT_TYPE).blockingHandler(this::saveOrUpdate, false);
        router.get().path("/menu/:menuId").produces(CONTENT_TYPE).blockingHandler(this::queryOne, false);
        router.get().path("/menu").produces(CONTENT_TYPE).blockingHandler(this::query, false);
    }

    public void saveOrUpdate(RoutingContext routingContext) {
        Menu menu = routingContext.getBodyAsJson().mapTo(Menu.class);
        if (StringUtils.isEmpty(menu.getCode())) {
            responseFailure(routingContext, "code must be filled");
            return;
        }

        if (StringUtils.isEmpty(menu.getName())) {
            responseFailure(routingContext, "name must be filled");
            return;
        }

        menuService.saveOrUpdate(menu);
        responseSuccess(routingContext);
    }

    public void delete(RoutingContext routingContext) {
        String menuId = routingContext.pathParam("menuId");
        if (StringUtils.isEmpty(menuId)) {
            responseFailure(routingContext, "menuId must be supply");
            return;
        }

        menuService.deleteById(Integer.valueOf(menuId));
        responseSuccess(routingContext);
    }

    public void queryOne(RoutingContext routingContext) {
        String menuId = routingContext.pathParam("menuId");
        if (StringUtils.isEmpty(menuId)) {
            responseFailure(routingContext, "menuId must be supply");
            return;
        }

        Menu menu = menuService.getMenuById(Integer.valueOf(menuId));
        responseSuccess(routingContext, menu);
    }

    public void query(RoutingContext routingContext) {
        MultiMap params = routingContext.queryParams();

        List<Menu> list = menuService.find(RequestUtils.getInteger(params, "parentId"), RequestUtils.getInteger(params, "offset"), RequestUtils.getInteger(params, "rows"));
        long count = menuService.count(RequestUtils.getInteger(params, "parentId"));

        Map data = new HashMap();
        data.put("list", list);
        data.put("total", count);

        responseSuccess(routingContext, data);
    }

}
