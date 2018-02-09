package com.github.app.api.handler.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.app.api.dao.domain.Role;
import com.github.app.api.dao.domain.RolePopedom;
import com.github.app.api.handler.UriHandler;
import com.github.app.api.services.RoleService;
import com.github.app.api.utils.RequestUtils;
import com.github.app.utils.JacksonUtils;
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
public class RoleHandler implements UriHandler {

    @Autowired
    private RoleService roleService;

    @Override
    public void registeUriHandler(Router router) {
        router.post().path("/role").produces(CONTENT_TYPE).blockingHandler(this::saveOrUpdate, false);
        router.delete().path("/role/:roleId").produces(CONTENT_TYPE).blockingHandler(this::delete, false);
        router.put().path("/role").produces(CONTENT_TYPE).blockingHandler(this::saveOrUpdate, false);
        router.get().path("/role/:roleId").produces(CONTENT_TYPE).blockingHandler(this::queryOne, false);
        router.get().path("/role").produces(CONTENT_TYPE).blockingHandler(this::query, false);
        router.put().path("/role/popedom").produces(CONTENT_TYPE).blockingHandler(this::saveRolePopedom, false);
    }

    public void saveOrUpdate(RoutingContext routingContext) {
        Role role = routingContext.getBodyAsJson().mapTo(Role.class);
        if (StringUtils.isEmpty(role.getName())) {
            responseFailure(routingContext, "name must be filled");
            return;
        }

        roleService.saveOrUpdate(role);
        responseSuccess(routingContext);
    }
    
    public void delete(RoutingContext routingContext) {
        String roleId = routingContext.pathParam("roleId");
        if (StringUtils.isEmpty(roleId)) {
            responseFailure(routingContext, "roleId must be supply");
            return;
        }

        roleService.deleteRoleById(Integer.valueOf(roleId));
        responseSuccess(routingContext);
    }

    public void queryOne(RoutingContext routingContext) {
        String roleId = routingContext.pathParam("roleId");
        if (StringUtils.isEmpty(roleId)) {
            responseFailure(routingContext, "roleId must be supply");
            return;
        }

        Role role = roleService.getRoleById(Integer.valueOf(roleId));
        responseSuccess(routingContext, role);
    }

    public void query(RoutingContext routingContext) {
        MultiMap params = routingContext.queryParams();

        List<Role> list = roleService.list(RequestUtils.getInteger(params, "offset"), RequestUtils.getInteger(params, "rows"));
        long count = roleService.count();

        Map data = new HashMap<>();
        data.put("list", list);
        data.put("total", count);

        responseSuccess(routingContext, data);
    }

    public void saveRolePopedom(RoutingContext routingContext) {
        String json = routingContext.getBodyAsString();

        List<RolePopedom> list = JacksonUtils.json2Object(json, new TypeReference<List<RolePopedom>>(){});

        roleService.deleteRolePopedomById(list.get(0).getRoleId(), null);
        roleService.addRolePopedoms(list);
        responseSuccess(routingContext);
    }
}
