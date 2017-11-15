package com.github.app.api.runner;

import com.github.app.api.config.SpringApplication;
import com.github.app.api.utils.CmdParase;
import com.github.app.api.verticles.HttpServerVerticle;
import com.github.app.api.verticles.SpringVerticleFactory;
import com.github.app.utils.LogbackLoaderUtils;
import com.github.app.utils.Runner;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.core.spi.VerticleFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ServerRunner implements Runner {
    private static Logger logger = LoggerFactory.getLogger(ServerRunner.class);

    @Override
    public void start(String[] args) {
        try {
            CmdParase.build(args);
            /**
             * set logback config to external config file
             */
            LogbackLoaderUtils.loadConfig(CmdParase.getInstance().getLogbackCfg());

            JsonObject config = CmdParase.getInstance().getServerCfg();

            /**
             * set vertx cache base directory
             */
            String cacheBase = config.getJsonObject("vertx").getString("cacheDirBase");
            System.setProperty("vertx.cacheDirBase", cacheBase);
            logger.info("set vertx cache directory to " + cacheBase);

            DeploymentOptions deploymentOptions = new DeploymentOptions();
            deploymentOptions.setConfig(config);

            Vertx vertx = Vertx.vertx();

            ApplicationContext context = new AnnotationConfigApplicationContext(SpringApplication.class);

            VerticleFactory verticleFactory = context.getBean(SpringVerticleFactory.class);
            vertx.registerVerticleFactory(verticleFactory);

            vertx.deployVerticle(verticleFactory.prefix() + ":" + HttpServerVerticle.class.getName(),
                    deploymentOptions, ar -> {
                if(ar.succeeded()) {
                    logger.info(" deploy success");
                } else {
                    logger.error("deploy err", ar.cause());
                    vertx.close();
                    System.exit(-3);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("err", e);
        }
    }
}
