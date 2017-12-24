package com.github.app.deploy;

import com.github.app.utils.LogbackLoaderUtils;
import com.github.app.utils.Runner;
import com.github.app.utils.ServerConstant;
import com.sun.tools.attach.VirtualMachine;
import org.apache.commons.exec.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;

public class HotDeployRunner implements Runner {
    private static final Logger logger = LoggerFactory.getLogger(HotDeployRunner.class);

    @Override
    public void start(String[] args) {
        try {
            LogbackLoaderUtils.loadConfig(System.getenv(ServerConstant.APP_HOME)
                    + File.separator + "config" + File.separator + "logback.xml");

            String[] pids = findApplicationPID();
            if (pids != null && pids.length > 0) {

                for (String pid : pids) {
                    VirtualMachine vm = VirtualMachine.attach(pid);

                    vm.loadAgent(System.getenv(ServerConstant.APP_HOME) + File.separator + "plugins"
                            + File.separator + "java-agent.jar" , "this param can pass to java agent agentmain args");
                    vm.detach();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("HotDeploy error happen", e);
        }
    }

    private String[] findApplicationPID() {
        String name = ManagementFactory.getRuntimeMXBean().getName();
        String pid = name.split("@")[0];

        String appKeyWord = Thread.currentThread().getStackTrace()[3].getClassName();

        CommandLine commandLine = new CommandLine("jps");
        commandLine.addArgument("-l");

        Executor executor = new DefaultExecutor();
        executor.setExitValue(0);

        ExecuteWatchdog watchdog = new ExecuteWatchdog(5 * 60000);
        executor.setWatchdog(watchdog);
        try {
            ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
            ByteArrayOutputStream outFileStream = new ByteArrayOutputStream();
            executor.setStreamHandler(new PumpStreamHandler(outFileStream, errorStream));

            String error = errorStream.toString("utf-8");
            logger.warn(error);
            int exitValue = executor.execute(commandLine);

            BufferedReader reader = new BufferedReader(new StringReader(outFileStream.toString("utf-8")));
            String line;
            List<String> appPids = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                if (line.contains(appKeyWord)) {
                    if (!line.contains(pid)) {
                        appPids.add(line.replaceAll(appKeyWord, "").trim());
                    }
                }
            }

            logger.info("export executor finish, exit value is " + exitValue);
            return appPids.toArray(new String[appPids.size()]);

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("deploy err", e);
        }

        return null;
    }
}
