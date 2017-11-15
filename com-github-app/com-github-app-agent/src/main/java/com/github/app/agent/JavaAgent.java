package com.github.app.agent;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;


public class JavaAgent {
    public static void agentmain(String args, Instrumentation inst) {

        try {
            String clsName = "com.github.app.api.handler.common.WelcomHandler";
            Class<?> c = Class.forName(clsName);

            File file = new File("/home/xsy/github/com-github-web-application/com-github-app/com-github-app-api/target/classes/com/github/app/api/handler/common/WelcomHandler.class");
            byte[] buffer = FileUtils.readFileToByteArray(file);

            ClassDefinition classDefinition = new ClassDefinition(c, buffer);

            inst.redefineClasses(classDefinition);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
