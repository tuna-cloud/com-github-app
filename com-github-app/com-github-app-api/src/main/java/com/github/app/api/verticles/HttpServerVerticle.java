package com.github.app.api.verticles;

import com.github.app.api.handler.UriHandler;
import com.github.iot.utils.ClassUtil;
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

import java.util.List;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

@Component
@Scope(SCOPE_PROTOTYPE)
public class HttpServerVerticle extends AbstractVerticle implements ApplicationContextAware {
    private static final Logger logger = LoggerFactory.getLogger(HttpServerVerticle.class);

    private ApplicationContext applicationContext;
    private HttpServer server;

    @Override
    public void start() throws Exception {
        super.start();

        JsonObject jsonObject = config();

        JsonObject httpsOption = jsonObject.getJsonObject("https.option");

        HttpServerOptions options = new HttpServerOptions().setSsl(false);
        if(httpsOption.getBoolean("enable")) {
            options.setSsl(true);
            options.setKeyCertOptions(new JksOptions()
                    .setPath(httpsOption.getJsonObject("keyCert").getString("path"))
                    .setPassword(httpsOption.getJsonObject("keyCert").getString("password")));
        } else {
            options.setSsl(false);
        }

        server = vertx.createHttpServer(options);

        Router router = Router.router(vertx);

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

    private void bindUri(Router router) {
        router.route().handler(CorsHandler.create("*")
                .allowedMethod(HttpMethod.GET)
                .allowedMethod(HttpMethod.POST)
                .allowedMethod(HttpMethod.DELETE)
                .allowedMethod(HttpMethod.PUT)
                .allowedMethod(HttpMethod.OPTIONS)
                .allowedHeader("X-Token")
                .allowedHeader("Content-Type"));

        router.route("/*").handler(LoggerHandler.create());
        router.route("/*").handler(TimeoutHandler.create(10000));
        router.route("/*").handler(ResponseTimeHandler.create());
        router.route("/*").handler(ResponseContentTypeHandler.create());
        router.route("/*").handler(BodyHandler.create());
        loadHandlers(router, "com.github.iot.api.handler.common");
//        router.route("/static/*").handler(StaticHandler.create().setAllowRootFileSystemAccess(true).setWebRoot("/home/xsy/github/vue/vueAdmin-template/dist"));

        /**
         * apiRouter will validate the token in the global interceptor
         */
        Router apiRouter = Router.router(vertx);
        loadHandlers(apiRouter, "com.github.iot.api.handler.interceptor");
        loadHandlers(apiRouter, "com.github.iot.api.handler.api");
        router.mountSubRouter("/api", apiRouter);

        /**
         * openRouter is fully opened api, have no interceptor to validate token and other user info
         */
        Router openRouter = Router.router(vertx);
        loadHandlers(openRouter, "com.github.iot.api.handler.open");
        router.mountSubRouter("/open", openRouter);
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
}
