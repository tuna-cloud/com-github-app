package com.github.app.api.handler.api;

import com.github.app.api.dao.domain.Account;
import com.github.app.api.handler.UriHandler;
import com.github.app.api.services.AccountService;
import com.github.app.api.utils.RequestUtils;
import io.vertx.core.MultiMap;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;

@Component
public class AccountHandler implements UriHandler {

	@Autowired
	private AccountService accountService;

	@Override
	public void registeUriHandler(Router router) {
		router.post().path("/account").produces("application/json;charset=UTF-8").blockingHandler(this::saveOrUpdate, false);
		router.delete().path("/account/:accountId").produces("application/json;charset=UTF-8").blockingHandler(this::delete, false);
		router.put().path("/account").produces("application/json;charset=UTF-8").blockingHandler(this::saveOrUpdate, false);
		router.get().path("/account/:accountId").produces("application/json;charset=UTF-8").blockingHandler(this::queryOne, false);
		router.get().path("/account").produces("application/json;charset=UTF-8").blockingHandler(this::query, false);
		router.get().path("/account/current/login").produces("application/json;charset=UTF-8").blockingHandler(this::currentLogin, false);
		router.put().path("/account/resetpwd/:accountId").produces("application/json;charset=UTF-8").blockingHandler(this::resetPassword, false);
	}

	public void saveOrUpdate(RoutingContext routingContext) {
		Account account = routingContext.getBodyAsJson().mapTo(Account.class);

		if (StringUtils.isEmpty(account.getAccount())) {
			responseFailure(routingContext, "account must be filled");
			return;
		}

		if (StringUtils.isEmpty(account.getPassword())) {
			responseFailure(routingContext, "password must be filled");
			return;
		}

		if (ObjectUtils.isEmpty(account.getRoleId())) {
			responseFailure(routingContext, "role must be selected");
			return;
		}

		accountService.saveOrUpdate(account);
		responseSuccess(routingContext);
	}

	public void delete(RoutingContext routingContext) {
		String accountId = routingContext.pathParam("accountId");
		if (StringUtils.isEmpty(accountId)) {
			responseFailure(routingContext, "accountId must be supply");
			return;
		}

		accountService.deleteByAccountId(Integer.valueOf(accountId));
		responseSuccess(routingContext);
	}

	public void query(RoutingContext routingContext) {
		MultiMap params = routingContext.queryParams();

		List<Account> list = accountService.findByKeyWord(RequestUtils.getInteger(params, "roleId"), params.get("keyword"), RequestUtils.getInteger(params, "offset"), RequestUtils.getInteger(params, "rows"));
		long count = accountService.countByKeyWord(RequestUtils.getInteger(params, "roleId"), params.get("keyword"));

		JsonObject data = new JsonObject();
		data.put("list", list);
		data.put("total", count);

		responseSuccess(routingContext, data);
	}

	public void queryOne(RoutingContext routingContext) {
		String accountId = routingContext.pathParam("accountId");
		if (StringUtils.isEmpty(accountId)) {
			responseFailure(routingContext, "accountId must be supply");
			return;
		}
		Account account = accountService.getAccountByAccountId(Integer.valueOf(accountId));
		responseSuccess(routingContext, account);
	}

	public void currentLogin(RoutingContext routingContext) {
		String acc = routingContext.get("account");
		if (StringUtils.isEmpty(acc)) {
			responseFailure(routingContext, "must be login first");
			return;
		}

		Account account = accountService.getAccountByAccountOrMobileOrEmail(acc, null, null);
		responseSuccess(routingContext, account);
	}

	public void resetPassword(RoutingContext routingContext) {
		String accountId = routingContext.pathParam("accountId");
		if (StringUtils.isEmpty(accountId)) {
			responseFailure(routingContext, "accountId must be supply");
			return;
		}
		accountService.resetPassword(Integer.valueOf(accountId));
		responseSuccess(routingContext);
	}
}