package com.github.app.api.runner;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.app.api.config.SpringApplication;
import com.github.app.api.utils.ConfigLoader;
import com.github.app.api.verticles.HttpServerVerticle;
import com.github.app.api.verticles.SpringVerticleFactory;
import com.github.app.utils.Runner;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.core.spi.VerticleFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ServerRunner implements Runner {
	private Logger logger = LogManager.getLogger(ServerRunner.class);

	@Override
	public String name() {
		return "server";
	}

	@Override
	public void usage(StringBuilder builder) {
		builder.append("\t -name server").append("\n");
		builder.append("\t\t the web server and static resources server").append("\n");
	}

	@Override
	public void start(String[] args) {
		try {
			/**
			 * config jackson mapper
			 */
			Json.mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false).configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).setSerializationInclusion(JsonInclude.Include.NON_NULL);

			JsonObject config = ConfigLoader.getServerCfg();
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

			vertx.deployVerticle(verticleFactory.prefix() + ":" + HttpServerVerticle.class.getName(), deploymentOptions, ar -> {
				if (ar.succeeded()) {
					logger.info(" http verticle deploy success");
				} else {
					logger.error("http verticle deploy err", ar.cause());
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
