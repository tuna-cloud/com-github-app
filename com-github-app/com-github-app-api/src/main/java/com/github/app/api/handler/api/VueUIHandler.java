package com.github.app.api.handler.api;

import com.github.app.api.dao.domain.Popedom;
import com.github.app.api.handler.UriHandler;
import io.vertx.ext.web.Router;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 该类主要注册前端页面上需要管理的权限信息
 */
@Component
public class VueUIHandler implements UriHandler {
    @Override
    public void registeUriHandler(Router router) {

    }

    @Override
    public void registePopedom(List<Popedom> list) {
        list.add(Popedom.builder().name("主面板").code("vuedashboard").build());
        list.add(Popedom.builder().name("系统管理").code("vuesystemmgr").build());
        list.add(Popedom.builder().name("账号管理").code("vueaccountmgr").build());
        list.add(Popedom.builder().name("密码修改").code("vueaccountpwd").build());
        list.add(Popedom.builder().name("账号信息").code("vueaccountinfo").build());
        list.add(Popedom.builder().name("角色管理").code("vuerolemgr").build());
        list.add(Popedom.builder().name("数据备份").code("vuedbback").build());
        list.add(Popedom.builder().name("日志管理").code("vuelogmgr").build());
    }
}
