package com.github.app.api.handler.interceptor;

import com.github.app.api.handler.UriHandler;
import com.github.app.api.services.AccountService;
import com.github.app.api.services.LogService;
import com.github.app.api.utils.AuthUtils;
import com.github.app.api.utils.ConfigLoader;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.KeyStoreOptions;
import io.vertx.ext.auth.User;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.auth.jwt.JWTAuthOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class AuthInterceptor implements UriHandler {
	private Logger logger = LogManager.getLogger(AuthInterceptor.class);
	private static JWTAuthOptions jwtAuthOptions;
	static {
		JsonObject sysConfig = ConfigLoader.getServerCfg().getJsonObject("jwt.keystore");
		jwtAuthOptions = new JWTAuthOptions().setKeyStore(new KeyStoreOptions().setPath(sysConfig.getString("path")).setPassword(sysConfig.getString("password")));
	}
	private JWTAuth jwtAuth;

	@Autowired
	private AccountService accountService;
	@Autowired
	private LogService logService;

	@Override
	public void registeUriHandler(Router router) {
		router.route().path("/*").order(0).blockingHandler(this::loginAuthentication, false);
		router.route().path("/*").order(1).blockingHandler(this::operationAuthentication, false);
	}

	/**
	 * 身份验证拦截器 验证服务调用者是否已登录系统
	 *
	 * @param routingContext
	 */
	public void loginAuthentication(RoutingContext routingContext) {
		try {
			String token = routingContext.request().getHeader("X-Token");

			if (StringUtils.isEmpty(token)) {
				response(routingContext, CODE_JWT_TOKEN_MISSING, "token must be filled");
				return;
			}

			if (jwtAuth == null) {
				jwtAuth = JWTAuth.create(routingContext.vertx(), jwtAuthOptions);
			}

			jwtAuth.authenticate(new JsonObject().put("jwt", token), res -> {
				if (res.succeeded()) {
					User theUser = res.result();
					String account = theUser.principal().getString("account");
					routingContext.put("account", account);
					routingContext.next();
				} else {
					response(routingContext, CODE_JWT_TOKEN_INVALIDATE, "token invalidate");
				}
			});
		} catch (Exception e) {
			responseFailure(routingContext, e);
		}
	}

	/**
	 * 操作拦截器 验证服务调用者是否有权限操作资源
	 *
	 * @param routingContext
	 */
	public void operationAuthentication(RoutingContext routingContext) {

		String account = routingContext.get("account");
		String authCode = AuthUtils.authCode(routingContext);
		String remark = AuthUtils.buildRequestInfo(routingContext);
		logService.addLog(accountService.getAccountByAccountOrMobileOrEmail(account, null, null), authCode, remark);

		routingContext.next();
	}

}
