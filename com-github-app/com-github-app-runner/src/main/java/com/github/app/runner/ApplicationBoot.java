package com.github.app.runner;

import com.github.app.api.runner.DatabaseBackupRunner;
import com.github.app.api.runner.InstallRunner;
import com.github.app.api.runner.RestoreRunner;
import com.github.app.api.runner.ServerRunner;
import com.github.app.deploy.HotDeployRunner;
import com.github.app.utils.Runner;
import io.vertx.core.cli.*;

import java.util.*;

public class ApplicationBoot {
    static Optional<CommandLine> cli(String[] args) {
        CLI cli = CLI.create("ApplicationBooter")
                .setSummary("An application runner instance")
                .addOption(new Option()
                        .setLongName("name")
                        .setShortName("name")
                        .setDefaultValue("server")
                        .setDescription("vert.x config file (in json format)")
                        .setRequired(false)
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
            System.exit(-1);
        }
        return Optional.ofNullable(commandLine);
    }

    static Map<String, Runner> runnerMap = new HashMap<>();
    static {
        runnerMap.put("server", new ServerRunner());
        runnerMap.put("install", new InstallRunner());
        runnerMap.put("backup", new DatabaseBackupRunner());
        runnerMap.put("restore", new RestoreRunner());
        runnerMap.put("redeploy", new HotDeployRunner());
    }

    public static void main(String[] args) {
        System.setProperty("vertx.logger-delegate-factory-class-name", "io.vertx.core.logging.SLF4JLogDelegateFactory");
        Optional<CommandLine> commandLine = cli(args);
        if(commandLine.isPresent()) {
            Arrays.asList(commandLine.get().getRawValueForOption(
                    commandLine.get().cli().getOption("name")).split(","))
                    .stream().forEach(svr -> {
                        if(runnerMap.containsKey(svr)) {
                            runnerMap.get(svr).start(args);
                        } else {
                            System.out.println("un know service:" + svr);
                            System.exit(-1);
                        }
            });
        }
    }
}
