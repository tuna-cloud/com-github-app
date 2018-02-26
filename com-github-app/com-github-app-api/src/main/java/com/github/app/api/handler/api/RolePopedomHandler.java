package com.github.app.api.handler.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.app.api.dao.domain.Popedom;
import com.github.app.api.dao.domain.Role;
import com.github.app.api.dao.domain.RolePopedom;
import com.github.app.api.handler.UriHandler;
import com.github.app.api.services.RolePodomService;
import com.github.app.api.utils.RequestUtils;
import com.github.app.utils.JacksonUtils;
import io.vertx.core.MultiMap;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RolePopedomHandler implements UriHandler {

    @Autowired
    private RolePodomService rolePodomService;

    @Override
    public void registeUriHandler(Router router) {
        router.post().path("/role").produces(CONTENT_TYPE).blockingHandler(this::saveOrUpdate, false);
        router.delete().path("/role/:roleId").produces(CONTENT_TYPE).blockingHandler(this::delete, false);
        router.put().path("/role").produces(CONTENT_TYPE).blockingHandler(this::saveOrUpdate, false);
        router.get().path("/role/:roleId").produces(CONTENT_TYPE).blockingHandler(this::queryOne, false);
        router.get().path("/role").produces(CONTENT_TYPE).blockingHandler(this::query, false);
        router.put().path("/role/popedom").produces(CONTENT_TYPE).blockingHandler(this::saveRolePopedom, false);
        router.get().path("/role/popedom/:roleId").produces(CONTENT_TYPE).blockingHandler(this::getRolePopedom, false);
        router.get().path("/role/popedom/vue/:roleId").produces(CONTENT_TYPE).blockingHandler(this::getRolePopedomVue, false);
        router.get().path("/role/popedom/api/:roleId").produces(CONTENT_TYPE).blockingHandler(this::getRolePopedomApi, false);
    }

    @Override
    public void registePopedom(List<Popedom> list) {
        list.add(new Popedom.Builder().name("*增加角色").code("/[a-zA-Z]+/role/" + HttpMethod.POST.name()).build());
        list.add(new Popedom.Builder().name("*删除角色").code("/[a-zA-Z]+/role/[0-9]+/" + HttpMethod.DELETE.name()).build());
        list.add(new Popedom.Builder().name("*更新角色").code("/[a-zA-Z]+/role/" + HttpMethod.PUT.name()).build());
        list.add(new Popedom.Builder().name("*查询角色").code("/[a-zA-Z]+/role/[0-9]+/" + HttpMethod.GET.name()).build());
        list.add(new Popedom.Builder().name("*查询所有角色").code("/[a-zA-Z]+/role/" + HttpMethod.GET.name()).build());
        list.add(new Popedom.Builder().name("*权限修改").code("/[a-zA-Z]+/role/popedom/" + HttpMethod.PUT.name()).build());
        list.add(new Popedom.Builder().name("*权限查询").code("/[a-zA-Z]+/role/popedom/[0-9]+/" + HttpMethod.GET.name()).build());
        list.add(new Popedom.Builder().name("*界面权限查询").code("/[a-zA-Z]+/role/popedom/vue/[0-9]+/" + HttpMethod.GET.name()).build());
        list.add(new Popedom.Builder().name("*接口权限查询").code("/[a-zA-Z]+/role/popedom/api/[0-9]+/" + HttpMethod.GET.name()).build());
    }

    public void saveOrUpdate(RoutingContext routingContext) {
        Role role = routingContext.getBodyAsJson().mapTo(Role.class);
        if (StringUtils.isEmpty(role.getName())) {
            responseFailure(routingContext, "name must be filled");
            return;
        }

        rolePodomService.saveOrUpdate(role);
        responseSuccess(routingContext);
    }
    
    public void delete(RoutingContext routingContext) {
        String roleId = routingContext.pathParam("roleId");
        if (StringUtils.isEmpty(roleId)) {
            responseFailure(routingContext, "roleId must be supply");
            return;
        }

        rolePodomService.deleteRoleById(Integer.valueOf(roleId));
        responseSuccess(routingContext);
    }

    public void queryOne(RoutingContext routingContext) {
        String roleId = routingContext.pathParam("roleId");
        if (StringUtils.isEmpty(roleId)) {
            responseFailure(routingContext, "roleId must be supply");
            return;
        }

        Role role = rolePodomService.getRoleById(Integer.valueOf(roleId));
        responseSuccess(routingContext, role);
    }

    public void query(RoutingContext routingContext) {
        MultiMap params = routingContext.queryParams();

        List<Role> list = rolePodomService.list(RequestUtils.getInteger(params, "offset"), RequestUtils.getInteger(params, "rows"));
        long count = rolePodomService.count();

        Map data = new HashMap<>();
        data.put("list", list);
        data.put("total", count);

        responseSuccess(routingContext, data);
    }

    public void saveRolePopedom(RoutingContext routingContext) {
        String json = routingContext.getBodyAsString();

        List<RolePopedom> list = JacksonUtils.json2Object(json, new TypeReference<List<RolePopedom>>(){});

        rolePodomService.deleteRolePopedomById(list.get(0).getRoleId(), null);
        rolePodomService.addRolePopedoms(list);
        responseSuccess(routingContext);
    }

    public void getRolePopedom(RoutingContext routingContext) {
        String roleId = routingContext.pathParam("roleId");
        if (StringUtils.isEmpty(roleId)) {
            responseFailure(routingContext, "roleId must be supply");
            return;
        }

        List<Popedom> list = rolePodomService.findPopedomByRoleId(Integer.valueOf(roleId));
        responseSuccess(routingContext, list);
    }

    public void getRolePopedomVue(RoutingContext routingContext) {
        String roleId = routingContext.pathParam("roleId");
        if (StringUtils.isEmpty(roleId)) {
            responseFailure(routingContext, "roleId must be supply");
            return;
        }

        List<Popedom> list = rolePodomService.findVuePopedomByRoleId(Integer.valueOf(roleId));
        responseSuccess(routingContext, list);
    }

    public void getRolePopedomApi(RoutingContext routingContext) {
        String roleId = routingContext.pathParam("roleId");
        if (StringUtils.isEmpty(roleId)) {
            responseFailure(routingContext, "roleId must be supply");
            return;
        }

        List<Popedom> list = rolePodomService.findApiPopedomByRoleId(Integer.valueOf(roleId));
        responseSuccess(routingContext, list);
    }
}
