package com.github.app.api.runner;

import com.github.app.api.services.SystemOperationService;
import com.github.app.api.services.impl.MySqlOperationServiceImpl;
import com.github.app.api.utils.CmdParase;
import com.github.app.utils.LogbackLoaderUtils;
import com.github.app.utils.Runner;
import io.vertx.core.cli.CLI;
import io.vertx.core.cli.CLIException;
import io.vertx.core.cli.CommandLine;
import io.vertx.core.cli.Option;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class RestoreMySqlDatabaseRunner implements Runner{
    private static Logger logger = LoggerFactory.getLogger(InitMySqlDataBaseRunner.class);

    static Optional<CommandLine> cli(String[] args) {
        CLI cli = CLI.create("MySql restore")
                .setSummary("An application runner instance for restore sql file to database")
                .addOption(new Option()
                        .setLongName("sql")
                        .setShortName("sql")
                        .setDescription("the sql file name but not include path")
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
            System.out.println(builder.toString());
            System.exit(-5);
        }
        return Optional.ofNullable(commandLine);
    }

    @Override
    public String name() {
        return "restoremysql";
    }

    @Override
    public void usage(StringBuilder builder) {
        builder.append("\t-name restoremysql -sql [the sql file to restore]").append("\n");
        builder.append("\t\t restore the back sql file to mysql database").append("\n");
    }

    @Override
    public void start(String[] args) {
        try {
            CmdParase.build(args);
            /**
             * set logback config to external config file
             */
            LogbackLoaderUtils.loadConfig(CmdParase.getInstance().getLogbackCfg());

            JsonObject config = CmdParase.getInstance().getServerCfg();

            Optional<CommandLine> commandLine = cli(args);

            if(commandLine.isPresent()) {
                String sql = commandLine.get().getRawValueForOption(commandLine.get().cli().getOption("sql"));
                SystemOperationService operationService = new MySqlOperationServiceImpl();
                operationService.restore(config, sql);
            } else {
                logger.error("-sql argument must be set");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("err", e);
        }
    }
}
