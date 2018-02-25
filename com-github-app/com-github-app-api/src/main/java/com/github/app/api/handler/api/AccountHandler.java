package com.github.app.api.handler.api;

import com.github.app.api.dao.domain.Account;
import com.github.app.api.dao.domain.Popedom;
import com.github.app.api.dao.domain.Role;
import com.github.app.api.handler.UriHandler;
import com.github.app.api.services.AccountService;
import com.github.app.api.services.RolePodomService;
import com.github.app.api.utils.RequestUtils;
import com.github.app.utils.MD5Utils;
import io.vertx.core.MultiMap;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.*;

@Component
public class AccountHandler implements UriHandler {

	@Autowired
	private AccountService accountService;
	@Autowired
	private RolePodomService rolePodomService;

	@Override
	public void registeUriHandler(Router router) {
		router.post().path("/account").produces(CONTENT_TYPE).blockingHandler(this::save, false);
		router.delete().path("/account/:accountId").produces(CONTENT_TYPE).blockingHandler(this::delete, false);
		router.put().path("/account").produces(CONTENT_TYPE).blockingHandler(this::update, false);
		router.put().path("/account/enable").produces(CONTENT_TYPE).blockingHandler(this::enable, false);
		router.get().path("/account/:accountId").produces(CONTENT_TYPE).blockingHandler(this::queryOne, false);
		router.get().path("/account").produces(CONTENT_TYPE).blockingHandler(this::query, false);
		router.get().path("/account/current/login").produces(CONTENT_TYPE).blockingHandler(this::currentLogin, false);
		router.put().path("/account/resetpwd/:accountId").produces(CONTENT_TYPE).blockingHandler(this::resetPassword, false);
		router.put().path("/account/editpwd").produces(CONTENT_TYPE).blockingHandler(this::editPassword, false);
	}

	@Override
	public void registePopedom(List<Popedom> list) {
		list.add(new Popedom.Builder().name("*帐号添加").code("/[a-zA-Z]+/account/" + HttpMethod.POST.name()).build());
		list.add(new Popedom.Builder().name("*账号删除").code("/[a-zA-Z]+/account/[0-9]+/" + HttpMethod.DELETE.name()).build());
		list.add(new Popedom.Builder().name("*账号信息修改").code("/[a-zA-Z]+/account/" + HttpMethod.PUT.name()).build());
		list.add(new Popedom.Builder().name("*账号停用启用").code("/[a-zA-Z]+/account/enable/" + HttpMethod.PUT.name()).build());
		list.add(new Popedom.Builder().name("*查询单个账号").code("/[a-zA-Z]+/account/[0-9]+/" + HttpMethod.GET.name()).build());
		list.add(new Popedom.Builder().name("*查询所有账号").code("/[a-zA-Z]+/account/" + HttpMethod.GET.name()).build());
		list.add(new Popedom.Builder().name("*当前登录账号").code("/[a-zA-Z]+/account/current/login/" + HttpMethod.GET.name()).build());
		list.add(new Popedom.Builder().name("*重置帐号密码").code("/[a-zA-Z]+/account/resetpwd/[0-9]+/" + HttpMethod.PUT.name()).build());
		list.add(new Popedom.Builder().name("*修改账号密码").code("/[a-zA-Z]+/account/editpwd/" + HttpMethod.PUT.name()).build());
	}

	public void enable(RoutingContext routingContext) {
		Account account = routingContext.getBodyAsJson().mapTo(Account.class);

		if(account.getAccountId() == 1) {
			responseFailure(routingContext, "超级管理员帐号不允许禁用");
			return;
		}

		accountService.changeAccountStatus(account.getAccountId());
		responseSuccess(routingContext);
	}

	public void save(RoutingContext routingContext) {
		Account account = routingContext.getBodyAsJson().mapTo(Account.class);

		if (StringUtils.isEmpty(account.getAccount())) {
			responseFailure(routingContext, "帐号不能为空");
			return;
		}

		if (ObjectUtils.isEmpty(account.getRoleId())) {
			responseFailure(routingContext, "角色不能为空");
			return;
		}

		accountService.saveOrUpdate(account);
		responseSuccess(routingContext);
	}

	/**
	 * 不允许修改账号密码状态角色信息
	 * @param routingContext
	 */
	public void update(RoutingContext routingContext) {
		Account account = routingContext.getBodyAsJson().mapTo(Account.class);

		if (StringUtils.isEmpty(account.getAccount())) {
			responseFailure(routingContext, "帐号不能为空");
			return;
		}

		if (ObjectUtils.isEmpty(account.getRoleId())) {
			responseFailure(routingContext, "角色不能为空");
			return;
		}

		Account dbAccount = accountService.getAccountByAccountOrMobileOrEmail(account.getAccount(), null, null);

		dbAccount.setName(account.getName());
		dbAccount.setSex(account.getSex());
		dbAccount.setMobile(account.getMobile());
		dbAccount.setEmail(account.getEmail());
		dbAccount.setAddress(account.getAddress());
		dbAccount.setRemark(account.getRemark());

		accountService.saveOrUpdate(dbAccount);
		responseSuccess(routingContext);
	}

	public void delete(RoutingContext routingContext) {
		String accountId = routingContext.pathParam("accountId");
		if (StringUtils.isEmpty(accountId)) {
			responseFailure(routingContext, "accountId参数缺失");
			return;
		}

		if(accountId.equalsIgnoreCase("1")) {
			responseFailure(routingContext, "超级管理员帐号不允许删除");
			return;
		}

		accountService.deleteByAccountId(Integer.valueOf(accountId));
		responseSuccess(routingContext);
	}

	public void query(RoutingContext routingContext) {
		MultiMap params = routingContext.queryParams();

		List<Account> list = accountService.findByKeyWord(RequestUtils.getInteger(params, "roleId"), params.get("keyword"), RequestUtils.getInteger(params, "offset"), RequestUtils.getInteger(params, "rows"));
		long count = accountService.countByKeyWord(RequestUtils.getInteger(params, "roleId"), params.get("keyword"));

		Map map = new HashMap();
		map.put("total", count);
		map.put("list", list);

		responseSuccess(routingContext, map);
	}

	public void queryOne(RoutingContext routingContext) {
		String accountId = routingContext.pathParam("accountId");
		if (StringUtils.isEmpty(accountId)) {
			responseFailure(routingContext, "accountId参数缺失");
			return;
		}
		Account account = accountService.getAccountByAccountId(Integer.valueOf(accountId));
		responseSuccess(routingContext, account);
	}

	public void currentLogin(RoutingContext routingContext) {
		String acc = routingContext.get("account");
		if (StringUtils.isEmpty(acc)) {
			responseFailure(routingContext, "未登录");
			return;
		}

		Account account = accountService.getAccountByAccountOrMobileOrEmail(acc, null, null);
		List<Popedom> list = rolePodomService.findVuePopedomByRoleId(account.getRoleId());

		Map map = new HashMap();
		map.put("account", account);
		Set<String> codes = new HashSet<>();
		for (Popedom popedom : list) {
			codes.add(popedom.getCode());
		}
		map.put("list", codes);

		responseSuccess(routingContext, map);
	}

	public void resetPassword(RoutingContext routingContext) {
		String accountId = routingContext.pathParam("accountId");
		if (StringUtils.isEmpty(accountId)) {
			responseFailure(routingContext, "accountId参数缺失");
			return;
		}
		accountService.resetPassword(Integer.valueOf(accountId));
		responseSuccess(routingContext);
	}

	public void editPassword(RoutingContext routingContext) {
		JsonObject jsonObject = routingContext.getBodyAsJson();

		if(!jsonObject.containsKey("oldPwd")) {
			responseFailure(routingContext, "oldPwd参数缺失");
			return;
		}

		if(!jsonObject.containsKey("newPwd")) {
			responseFailure(routingContext, "newPwd参数缺失");
			return;
		}

		if(!jsonObject.containsKey("newPwdCheck")) {
			responseFailure(routingContext, "newPwdCheck参数缺失");
			return;
		}

		String acc = routingContext.get("account");
		if (StringUtils.isEmpty(acc)) {
			responseFailure(routingContext, "未登录");
			return;
		}

		Account account = accountService.getAccountByAccountOrMobileOrEmail(acc, null, null);

		if (!MD5Utils.validateMd5WithSalt(jsonObject.getString("oldPwd"), account.getPassword())) {
			responseFailure(routingContext, "原始密码输入有误");
			return;
		}

		if (!jsonObject.getString("newPwd").equals(jsonObject.getString("newPwdCheck"))) {
			responseFailure(routingContext, "新密码两次输入不一致");
			return;
		}

		account.setPassword(jsonObject.getString("newPwd"));
		accountService.saveOrUpdate(account);
		responseSuccess(routingContext);
	}
}