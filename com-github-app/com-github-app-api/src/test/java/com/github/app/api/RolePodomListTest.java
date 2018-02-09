package com.github.app.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.app.api.dao.domain.RolePopedom;
import com.github.app.utils.JacksonUtils;
import io.vertx.core.json.JsonObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class RolePodomListTest {

    @Test
    public void test() throws Exception {
        List<RolePopedom> list = new ArrayList<>();
        RolePopedom r1 = new RolePopedom();
        r1.setMenuId(1);
        r1.setRoleId(1);
        list.add(r1);
        r1.setMenuId(2);
        list.add(r1);
        r1.setMenuId(3);
        list.add(r1);
        r1.setMenuId(4);
        list.add(r1);

        String msg = JacksonUtils.object2JsonStr(list);
        System.out.println(msg);

        List<RolePopedom> list1 = JacksonUtils.json2Object(msg, new TypeReference<List<RolePopedom>>(){});
        System.out.println(list1.size());

        JsonObject object = new JsonObject();
        object.put("list", list);
        System.out.println(object.toString());
    }
}
