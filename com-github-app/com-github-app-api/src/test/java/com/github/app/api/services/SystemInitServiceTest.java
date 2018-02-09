package com.github.app.api.services;

import com.github.app.api.dao.domain.Account;
import com.github.app.api.dao.domain.Menu;
import com.github.app.api.dao.domain.Role;
import com.github.app.api.dao.domain.RolePopedom;
import com.github.app.api.handler.UriHandler;
import com.github.app.api.utils.AppContext;
import com.github.app.utils.ClassUtil;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.impl.RouteImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SystemInitServiceTest extends BaseServiceTest {

	@Autowired
	private AccountService accountService;
	@Autowired
	private LogService logService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private MenuService menuService;

//	@Test
	public void systemInit() {
	    // 清表
		clearDB();
		// 创建管理员角色
		createAdminRole();
		// 创建管理员帐号
		createAdminAccount();
		// 扫描代码，生成接口权限被控资源
		genMenuMetaData();
		// 授予超级管理员所有操作权限
		genAdminAuthData();
	}

	public void clearDB() {
		logService.truncate();
		accountService.truncate();
		roleService.truncate();
		menuService.truncate();
	}

	public void createAdminRole() {
		Role role = new Role();
		role.setName("超级管理员");
		roleService.saveOrUpdate(role);
	}

	public void createAdminAccount() {

		Account account = new Account();
		account.setAccount("administrator");
		account.setPassword("123456");
		account.setRoleId(1);
		account.setName("超级管理员");
		account.setSex("男");
		account.setEmail("xsy870712@163.com");
		account.setMobile("15315086265");
		account.setIsEnable((short) 1);

		accountService.saveOrUpdate(account);
	}

	/**
	 * 生成菜单元数据
	 */
	public void genMenuMetaData() {
		String packageName = "com.github.app.api.handler.api";
		try {
			Router router = Router.router(Vertx.vertx());

			List<Class<?>> uriHandlers = ClassUtil.getClasses(packageName);
			for (Class<?> cls : uriHandlers) {
				try {
					Object bean = AppContext.getContext().getBean(cls);
					if (bean instanceof UriHandler) {
						UriHandler uriHandler = (UriHandler) bean;
						uriHandler.registeUriHandler(router);
					}
				} catch (Exception e) {
				}
			}

			List<Route> list = router.getRoutes();
			Set<String> codes = new HashSet<>();
			for (Route route : list) {
				codes.add(authCode(route));
			}

			for (String code : codes) {
				Menu menu = new Menu();
				menu.setCode(code);
				menu.setIsShow(0);
				menu.setIsDropDown(0);
				if (code.contains("GET")) {
					menu.setName("查询");
				} else if (code.contains("POST")) {
					menu.setName("新增");
				} else if (code.contains("PUT")) {
					menu.setName("修改");
				} else if (code.contains("DELETE")) {
					menu.setName("删除");
				}
				menu.setName(menu.getName() + "--" + code.substring(code.lastIndexOf("/")));
				menuService.saveOrUpdate(menu);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 给超级管理员授予全部权限
	 */
	public void genAdminAuthData() {
		List<Menu> list = menuService.find(null);
		List<RolePopedom> popedoms = new ArrayList<>();
		for (Menu menu : list) {
			RolePopedom popedom = new RolePopedom();
			popedom.setMenuId(menu.getMenuId());
			popedom.setRoleId(1);
			popedoms.add(popedom);
		}

		roleService.addRolePopedoms(popedoms);
	}

	private String authCode(Route route) {
		try {
			Class cls = RouteImpl.class;
			Field field = cls.getDeclaredField("methods");
			field.setAccessible(true);
			Set<HttpMethod> methods = (Set<HttpMethod>) field.get(route);
			String mn = null;
			for (HttpMethod method : methods) {
				mn = method.name();
			}

			String code = "/api" + route.getPath();
			int idx1 = code.indexOf("/");
			int idx2 = code.indexOf("/", idx1 + 1);
			int idx3 = code.indexOf("/", idx2 + 1);

			if (idx3 != -1) {
				code = code.substring(0, idx3);
			}

			code = String.format("[%s][%s]", mn, code);
			return code;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
