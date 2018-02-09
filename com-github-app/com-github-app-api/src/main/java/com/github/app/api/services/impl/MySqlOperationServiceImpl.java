package com.github.app.api.services.impl;

import com.github.app.api.services.SystemOperationService;
import com.github.app.utils.ServerEnvConstant;
import io.vertx.core.json.JsonObject;
import org.apache.commons.exec.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class MySqlOperationServiceImpl implements SystemOperationService {
    private Logger logger = LogManager.getLogger(MySqlOperationServiceImpl.class);

    @Override
    public void install(JsonObject jsonObject) {
        logger.info("begin import mysql data");
        JsonObject datasource = jsonObject.getJsonObject("datasource");

        String url = datasource.getString("url");

        String host = parseHost(url);
        String port = parsePort(url);
        String databaseName = parseDatabaseName(url);

        String sqlFile = System.getenv(ServerEnvConstant.APP_HOME)
                + File.separator + "data"
                + File.separator + databaseName + ".sql";

        CommandLine commandLine = new CommandLine("mysql");
        commandLine.addArgument("--host=" + host);
        commandLine.addArgument("-P" + port);
        commandLine.addArgument("-u" + datasource.getString("username"));
        commandLine.addArgument("-p" + datasource.getString("password"));

        exeMysqlImport(sqlFile, commandLine);
    }


    @Override
    public void backup(JsonObject jsonObject) {
        logger.info("begin export mysql data");
        JsonObject datasource = jsonObject.getJsonObject("datasource");

        String url = datasource.getString("url");
        String host = parseHost(url);
        String port = parsePort(url);
        String databaseName = parseDatabaseName(url);

        String outputFileName = System.getenv(ServerEnvConstant.APP_HOME)
                + File.separator + "data"
                + File.separator + "backup"
                + File.separator + "" + LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyMMddHHmmss")) + ".sql";

        CommandLine commandLine = new CommandLine("mysqldump");
        commandLine.addArgument("--host=" + host, false);
        commandLine.addArgument("-P" + port);
        commandLine.addArgument("-u" + datasource.getString("username"));
        commandLine.addArgument("-p" + datasource.getString("password"));
        commandLine.addArgument(databaseName, false);

        Executor executor = new DefaultExecutor();
        executor.setExitValue(0);

        ExecuteWatchdog watchdog = new ExecuteWatchdog(5*60000);
        executor.setWatchdog(watchdog);

        try {
            ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
            OutputStream outFileStream = new FileOutputStream(outputFileName);
            executor.setStreamHandler(new PumpStreamHandler(outFileStream, errorStream));

            String error = errorStream.toString("utf-8");
            logger.info(error);
            int exitValue = executor.execute(commandLine);
            logger.info("export executor finish, exit value is " + exitValue);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("export mysql data failed");
            logger.error(e.getLocalizedMessage());
        }
    }

    @Override
    public void restore(JsonObject jsonObject, String fileName) {
        logger.info("begin import mysql data");
        JsonObject datasource = jsonObject.getJsonObject("datasource");

        String url = datasource.getString("url");

        String host = parseHost(url);
        String port = parsePort(url);
        String databaseName = parseDatabaseName(url);

        String sqlFileName = System.getenv(ServerEnvConstant.APP_HOME)
                + File.separator + "data"
                + File.separator + "backup"
                + File.separator + fileName;

        CommandLine commandLine = new CommandLine("mysql");
        commandLine.addArgument("--host=" + host);
        commandLine.addArgument("-P" + port);
        commandLine.addArgument("-u" + datasource.getString("username"));
        commandLine.addArgument("-p" + datasource.getString("password"));
        commandLine.addArgument("-D" + databaseName);

        exeMysqlImport(sqlFileName, commandLine);
    }

    /**
     *
     * @param fileName
     * @param commandLine
     */
    private void exeMysqlImport(String fileName, CommandLine commandLine) {
        Executor executor = new DefaultExecutor();
        executor.setExitValue(0);

        ExecuteWatchdog watchdog = new ExecuteWatchdog(5*60000);
        executor.setWatchdog(watchdog);
        ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
        try {
            InputStream sqlFileStream = new FileInputStream(fileName);
            executor.setStreamHandler(new PumpStreamHandler(null, errorStream, sqlFileStream));

            logger.info(commandLine.toString());
            int exitValue = executor.execute(commandLine);

            // some time later the result handler callback was invoked so we
            // can safely request the exit value
            logger.info("import executor finish, exit value is " + exitValue);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("import mysql data failed");
            logger.error(e.getLocalizedMessage());
            logger.error(errorStream.toString());
        }
    }

    private String parseHost(String uri) {
        String tmp = uri.substring(13);
        tmp = tmp.substring(0, tmp.indexOf(":"));
        return tmp;
    }

    private String parsePort(String uri) {
        String host = parseHost(uri);
        String tmp = uri.substring(uri.indexOf(host) + host.length() + 1);
        tmp = tmp.substring(0, tmp.indexOf("/"));
        return tmp;
    }

    private String parseDatabaseName(String uri) {
        String port = parsePort(uri);
        String tmp = uri.substring(uri.indexOf(port) + port.length() + 1);
        tmp = tmp.substring(0, tmp.indexOf("?"));
        return tmp;
    }
}
