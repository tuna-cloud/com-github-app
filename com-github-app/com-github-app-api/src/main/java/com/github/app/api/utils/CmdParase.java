package com.github.app.api.utils;

import com.github.app.utils.ServerConstant;
import io.vertx.core.cli.CLI;
import io.vertx.core.cli.CLIException;
import io.vertx.core.cli.CommandLine;
import io.vertx.core.cli.Option;
import io.vertx.core.json.JsonObject;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class CmdParase {
    private static Logger logger = LoggerFactory.getLogger(CmdParase.class);

    private CommandLine commandLine;
    private static CmdParase cmdParase;

    public static void build(String[] args) {
        cmdParase = new CmdParase(args);
    }

    public static CmdParase getInstance() {
        return cmdParase;
    }

    private CmdParase(String[] args) {
        CLI cli = CLI.create("service config")
                .setSummary("service instance configuration")
                .addOption(new Option()
                        .setLongName("logback")
                        .setShortName("logback")
                        .setDescription("logback config xml file")
                        .setRequired(false)
                ).addOption(new Option()
                        .setLongName("cfg")
                        .setShortName("cfg")
                        .setDescription("application server configuration")
                        .setRequired(false)
                );

        try {
            List<String> userCommandLineArguments = Arrays.asList(args);
            commandLine = cli.parse(userCommandLineArguments);
        } catch(CLIException e) {
            // usage
            StringBuilder builder = new StringBuilder();
            cli.usage(builder);
            logger.error(builder.toString());
            System.exit(-2);
        }
    }

    public String getLogbackCfg() {
        String path = commandLine.getRawValueForOption(commandLine.cli().getOption("logback"));
        if(path == null) {
            path = System.getenv(ServerConstant.APP_HOME) + File.separator + "config" + File.separator + "logback.xml";
        }
        return path;
    }

    public JsonObject getServerCfg() {
        String path = commandLine.getRawValueForOption(commandLine.cli().getOption("cfg"));
        if(path == null) {
            path = System.getenv(ServerConstant.APP_HOME) + File.separator + "config" + File.separator + "server.json";
        }

        try {

            String confFileContent = FileUtils.readFileToString(new File(path), "UTF-8");
            confFileContent = confFileContent.replaceAll("\\$\\{" + ServerConstant.APP_HOME + "}", System.getenv(ServerConstant.APP_HOME));
            logger.info("server.json:" + confFileContent);
            return new JsonObject(confFileContent);
        }  catch (Exception e) {
            logger.error("server.json file missing");
        }
        System.exit(-1);
        return null;
    }

}
