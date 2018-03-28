package com.github.app.deploy;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.StringReader;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.exec.*;

import com.github.app.utils.ServerEnvConstant;
import com.sun.tools.attach.VirtualMachine;

public class HotDeployRunner {

    public String name() {
        return "deploy";
    }

    public void usage(StringBuilder builder) {
        builder.append("\t-name deploy\n");
        builder.append("\t\tdeploy the jar and class dynamic,is developing ... \n");
    }

    public void start(String[] args) {
        try {
            Thread.sleep(3000);
            String[] pids = findApplicationPID();
            if (pids != null && pids.length > 0) {

                for (String pid : pids) {
                    VirtualMachine vm = VirtualMachine.attach(pid);

                    vm.loadAgent(System.getenv(ServerEnvConstant.APP_HOME) + File.separator + "plugins"
                            + File.separator + "java-agent.jar" , "this param can pass to java agent agentmain args");
                    vm.detach();
                }
            }

            Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
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

            return appPids.toArray(new String[appPids.size()]);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
