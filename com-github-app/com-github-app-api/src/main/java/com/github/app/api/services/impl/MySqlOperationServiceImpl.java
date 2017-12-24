package com.github.app.api.services.impl;

import com.github.app.api.services.SystemOperationService;
import com.github.app.utils.ServerConstant;
import io.vertx.core.json.JsonObject;
import org.apache.commons.exec.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MySqlOperationServiceImpl implements SystemOperationService {
    private static final Logger logger = LoggerFactory.getLogger(MySqlOperationServiceImpl.class);

    @Override
    public void install(JsonObject jsonObject) {
        logger.info("begin import mysql data");
        JsonObject datasource = jsonObject.getJsonObject("datasource");

        String url = datasource.getString("url");

        String host = url.substring(13, url.indexOf(ServerConstant.DATABASE_NAME) - 1);
        host = host.substring(0, host.indexOf(":"));
        String port = url.substring(url.indexOf(":") + 1, url.indexOf(ServerConstant.DATABASE_NAME) - 1);
        port = port.substring(port.lastIndexOf(":") + 1);
        String sqlFile = System.getenv(ServerConstant.APP_HOME)
                + File.separator + "data" + File.separator + ServerConstant.DATABASE_NAME + ".sql";

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

        String host = url.substring(13, url.indexOf(ServerConstant.DATABASE_NAME) - 1);
        host = host.substring(0, host.indexOf(":"));
        String port = url.substring(url.indexOf(":") + 1, url.indexOf(ServerConstant.DATABASE_NAME) - 1);
        port = port.substring(port.lastIndexOf(":") + 1);
        String outputFileName = System.getenv(ServerConstant.APP_HOME)
                + File.separator + "data"
                + File.separator + "backup"
                + File.separator + "" + LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyMMddHHmmss")) + ".sql";

        CommandLine commandLine = new CommandLine("mysqldump");
        commandLine.addArgument("--host=" + host, false);
        commandLine.addArgument("-P" + port);
        commandLine.addArgument("-u" + datasource.getString("username"));
        commandLine.addArgument("-p" + datasource.getString("password"));
        commandLine.addArgument(ServerConstant.DATABASE_NAME, false);

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

        String host = url.substring(13, url.indexOf(ServerConstant.DATABASE_NAME) - 1);
        host = host.substring(0, host.indexOf(":"));
        String port = url.substring(url.indexOf(":") + 1, url.indexOf(ServerConstant.DATABASE_NAME) - 1);
        port = port.substring(port.lastIndexOf(":") + 1);

        String sqlFileName = System.getenv(ServerConstant.APP_HOME)
                + File.separator + "data"
                + File.separator + "backup"
                + File.separator + fileName;

        CommandLine commandLine = new CommandLine("mysql");
        commandLine.addArgument("--host=" + host);
        commandLine.addArgument("-P" + port);
        commandLine.addArgument("-u" + datasource.getString("username"));
        commandLine.addArgument("-p" + datasource.getString("password"));
        commandLine.addArgument("-D" + ServerConstant.DATABASE_NAME);

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
}
