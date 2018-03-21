package com.github.app.api.verticles;

import static com.github.app.api.utils.SessionConstant.SESSION_STORE_MAP;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.github.app.api.config.SpringApplication;
import com.github.app.api.dao.domain.Popedom;
import com.github.app.api.handler.UriHandler;
import com.github.app.api.utils.PopedomContext;
import com.github.app.utils.ClassUtil;
import com.github.app.utils.ServerEnvConstant;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.core.net.JksOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.*;
import io.vertx.ext.web.sstore.LocalSessionStore;

public class HttpServerVerticle extends AbstractVerticle {
	private Logger logger = LogManager.getLogger(HttpServerVerticle.class);

	private ApplicationContext applicationContext;
	private HttpServer server;
	private Router router;

	@Override
	public void start(Future<Void> startFuture) throws Exception {
		applicationContext = new AnnotationConfigApplicationContext(SpringApplication.class);

		JsonObject jsonObject = config();

		JsonObject httpsOption = jsonObject.getJsonObject("https.option");

		HttpServerOptions options = new HttpServerOptions().setSsl(false);
		if (httpsOption.getBoolean("enable")) {
			options.setSsl(true);
			options.setKeyCertOptions(new JksOptions().setPath(httpsOption.getJsonObject("keyCert").getString("path")).setPassword(httpsOption.getJsonObject("keyCert").getString("password")));
		} else {
			options.setSsl(false);
		}

		server = vertx.createHttpServer(options);

		router = Router.router(vertx);

        setupRouter(router);

		server.requestHandler(router::accept).listen(jsonObject.getInteger("api.bind.port", 8080), ar -> {
			if (ar.succeeded()) {
				logger.info("api server start successfully, bind port: " + server.actualPort());
				startFuture.complete();
			} else {
				logger.error("api server start failed, reason:" + ar.cause().getLocalizedMessage());
				startFuture.fail(ar.cause());
			}
		});
	}

	@Override
	public void stop(Future<Void> stopFuture) throws Exception {
	    router.clear();
		server.close(future -> {
			if (future.succeeded()) {
				stopFuture.complete();
			}
		});
	}

	private void setupRouter(Router rootRouter) {
		rootRouter.route().handler(CorsHandler.create(".*").allowedMethod(HttpMethod.GET).allowedMethod(HttpMethod.POST).allowedMethod(HttpMethod.DELETE).allowedMethod(HttpMethod.PUT).allowedMethod(HttpMethod.OPTIONS).allowCredentials(true).allowedHeader("X-Token").allowedHeader("Cookie").allowedHeader("Accept").allowedHeader("Accept-Encoding").allowedHeader("Accept-Language").allowedHeader("Connection").allowedHeader("Host").allowedHeader("Referer").allowedHeader("Origin").allowedHeader("Content-Type"));

		CookieHandler cookieHandler = CookieHandler.create();
		SessionHandler sessionHandler = SessionHandler.create(LocalSessionStore.create(vertx, SESSION_STORE_MAP, 30 * 60 * 1000));

		rootRouter.route().pathRegex("^(?!/static).*").handler(cookieHandler);
		rootRouter.route().pathRegex("^(?!/static).*").handler(sessionHandler);

		rootRouter.route().handler(LoggerHandler.create());
		rootRouter.route().handler(TimeoutHandler.create(8000));
		rootRouter.route().handler(ResponseTimeHandler.create());
		rootRouter.route().handler(ResponseContentTypeHandler.create());
		rootRouter.route().handler(BodyHandler.create());

		loadHandlers(rootRouter, "com.github.app.api.handler.common");
		rootRouter.route("/static/*").blockingHandler(StaticHandler.create().setAllowRootFileSystemAccess(true).setWebRoot(System.getenv(ServerEnvConstant.APP_HOME) + File.separator + "web"), false);

		/**
		 * apiRouter will validate the token in the global interceptor
		 */
		Router apiRouter = Router.router(vertx);
		loadHandlers(apiRouter, "com.github.app.api.handler.interceptor");
		loadHandlers(apiRouter, "com.github.app.api.handler.api");
		rootRouter.mountSubRouter("/api", apiRouter);

		/**
		 * openRouter is fully opened api, have no interceptor to validate token and other user info
		 */
		Router openRouter = Router.router(vertx);
		loadHandlers(openRouter, "com.github.app.api.handler.open");
		rootRouter.mountSubRouter("/open", openRouter);
	}

	private void loadHandlers(Router router, String packageName) {
		List<Class<?>> uriHandlers = ClassUtil.getClasses(packageName);
		List<Popedom> popedoms = new ArrayList<>();
		for (Class<?> cls : uriHandlers) {
			try {
				Object bean = applicationContext.getBean(cls);
				if (bean instanceof UriHandler) {
					UriHandler uriHandler = (UriHandler) bean;
					uriHandler.registeUriHandler(router);
					uriHandler.registePopedom(popedoms);
				}
			} catch (Exception e) {
				logger.warn(e.getLocalizedMessage());
			}
		}

		PopedomContext.getInstance().addPopedoms(popedoms);
	}

	/**
	 * 动态挂载handler
	 *
	 * @param uriHandler
	 */
	public void addRoute(UriHandler uriHandler) {
		uriHandler.registeUriHandler(router);
	}
}
