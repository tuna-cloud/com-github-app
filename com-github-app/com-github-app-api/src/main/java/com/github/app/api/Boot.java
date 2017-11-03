package com.github.app.api;

import com.github.app.api.config.SpringApplication;
import com.github.app.api.verticles.HttpServerVerticle;
import com.github.app.api.verticles.SpringVerticleFactory;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.cli.CLI;
import io.vertx.core.cli.CLIException;
import io.vertx.core.cli.CommandLine;
import io.vertx.core.cli.Option;
import io.vertx.core.json.JsonObject;
import io.vertx.core.spi.VerticleFactory;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.File;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class Boot {
    private static Logger logger = LoggerFactory.getLogger(Boot.class);

    static CommandLine cli(String[] args) {
        CLI cli = CLI.create("java -jar <iot-service>-fat.jar")
                .setSummary("An iot service instance")
                .addOption(new Option()
                        .setLongName("conf")
                        .setShortName("c")
                        .setDescription("vert.x config file (in json format)")
                        .setRequired(true)
                );

        // parsing
        CommandLine commandLine = null;
        try {
            List<String> userCommandLineArguments = Arrays.asList(args);
            commandLine = cli.parse(userCommandLineArguments);
        } catch(CLIException e) {
            // usage
            StringBuilder builder = new StringBuilder();
            cli.usage(builder);
            logger.error(builder.toString());
        }
        return commandLine;
    }

    public static void main(String[] args) {
        try {
            CommandLine commandLine = cli(args);

            String confFileContent = null;

            if (commandLine == null) {
                InputStream inputStream = Boot.class.getResourceAsStream("/app-config-default.json");
                byte[] buffer = new byte[inputStream.available()];
                inputStream.read(buffer);
                confFileContent = new String(buffer);
            } else {
                String cfgFilePath = commandLine.getOptionValue("c");
                confFileContent = FileUtils.readFileToString(new File(cfgFilePath), "UTF-8");
            }

            logger.info("app-config.json:" + confFileContent);

            DeploymentOptions deploymentOptions = new DeploymentOptions();

            JsonObject config = new JsonObject(confFileContent);
            deploymentOptions.setConfig(config);

            Vertx vertx = Vertx.vertx();

            ApplicationContext context = new AnnotationConfigApplicationContext(SpringApplication.class);

            VerticleFactory verticleFactory = context.getBean(SpringVerticleFactory.class);
            vertx.registerVerticleFactory(verticleFactory);

            vertx.deployVerticle(verticleFactory.prefix() + ":" + HttpServerVerticle.class.getName(), deploymentOptions, ar -> {
                if(ar.succeeded()) {
                    logger.info(" deploy success");
                } else {
                    logger.error("deploy err", ar.cause());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("err", e);
        }
    }
}
