package com.github.app.api.verticles;

import com.github.app.api.handler.UriHandler;
import com.github.app.utils.ClassUtil;
import com.github.app.utils.ServerConstant;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.core.net.JksOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_SINGLETON;

@Component
@Scope(SCOPE_SINGLETON)
public class HttpServerVerticle extends AbstractVerticle implements ApplicationContextAware {
    private static final Logger logger = LoggerFactory.getLogger(HttpServerVerticle.class);

    private ApplicationContext applicationContext;
    private HttpServer server;
    private Router router;

    @Override
    public void start() throws Exception {
        super.start();

        JsonObject jsonObject = config();

        JsonObject httpsOption = jsonObject.getJsonObject("https.option");

        HttpServerOptions options = new HttpServerOptions().setSsl(false);
        if (httpsOption.getBoolean("enable")) {
            options.setSsl(true);
            options.setKeyCertOptions(new JksOptions()
                    .setPath(httpsOption.getJsonObject("keyCert").getString("path"))
                    .setPassword(httpsOption.getJsonObject("keyCert").getString("password")));
        } else {
            options.setSsl(false);
        }

        server = vertx.createHttpServer(options);

        router = Router.router(vertx);

        vertx.executeBlocking(future -> {
            server.requestHandler(router::accept).listen(jsonObject.getInteger("api.bind.port", 8080), ar -> {
                if (ar.succeeded()) {
                    logger.info("api server start successfully, bind port: " + server.actualPort());
                    future.complete();
                } else {
                    logger.error("api server start failed, reason:" + ar.cause().getLocalizedMessage());
                    future.fail(ar.cause());
                }
            });
        }, asyncResult -> {
            if (asyncResult.failed()) {
                logger.error("", asyncResult.cause());
                vertx.close();
            } else {
                bindUri(router);
            }
        });
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }

    private void bindUri(Router rootRouter) {
        rootRouter.route().handler(CorsHandler.create("*")
                .allowedMethod(HttpMethod.GET)
                .allowedMethod(HttpMethod.POST)
                .allowedMethod(HttpMethod.DELETE)
                .allowedMethod(HttpMethod.PUT)
                .allowedMethod(HttpMethod.OPTIONS)
                .allowedHeader("X-Token")
                .allowedHeader("Content-Type"));

        rootRouter.route("/*").handler(LoggerHandler.create());
        rootRouter.route("/*").handler(TimeoutHandler.create(10000));
        rootRouter.route("/*").handler(ResponseTimeHandler.create());
        rootRouter.route("/*").handler(ResponseContentTypeHandler.create());
        rootRouter.route("/*").handler(BodyHandler.create());
        loadHandlers(rootRouter, "com.github.app.api.handler.common");
        rootRouter.route("/static/*").handler(StaticHandler.create()
                .setAllowRootFileSystemAccess(true)
                .setWebRoot(System.getenv(ServerConstant.APP_HOME) + File.separator + "web"));

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
        for (Class<?> cls : uriHandlers) {
            UriHandler uriHandler = (UriHandler) applicationContext.getBean(cls);
            uriHandler.registeUriHandler(router);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
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
