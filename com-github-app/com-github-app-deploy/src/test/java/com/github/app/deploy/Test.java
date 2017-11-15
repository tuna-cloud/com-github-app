package com.github.app.deploy;

import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

public class Test {
    public static void main(String[] args) throws Exception {
        JarFile jarFile = new JarFile("/home/xsy/github/com-github-web-application/com-github-app/application-home/libs/com-github-app-runner-1.0.0.jar");
        jarFile.stream().map(ZipEntry::getName).forEach(System.out::println);
        JarEntry entry = jarFile.getJarEntry("com/github/app/runner/ApplicationBoot.class");
        System.out.println(entry.getName());
        jarFile.getInputStream(entry);
    }
}
