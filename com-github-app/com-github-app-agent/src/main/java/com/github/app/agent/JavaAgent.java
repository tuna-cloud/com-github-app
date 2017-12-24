package com.github.app.agent;

import java.lang.instrument.Instrumentation;
import java.util.jar.JarFile;


public class JavaAgent {
    public static void agentmain(String args, Instrumentation inst) {

        try {
//            String clsName = "com.github.app.api.handler.common.HotLoadHandler";
//            Class<?> c = Class.forName(clsName);
//
//            File file = new File("/home/xsy/github/com-github-web-application/com-github-app/com-github-app-api/target/classes/com/github/app/api/handler/common/HotLoadHandler.class");
//            byte[] buffer = FileUtils.readFileToByteArray(file);
//
//            ClassDefinition classDefinition = new ClassDefinition(c, buffer);
//            inst.redefineClasses(classDefinition);

            JarFile jarFile = new JarFile("/home/xsy/test.jar");
            inst.appendToSystemClassLoaderSearch(jarFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
