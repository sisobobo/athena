package io.github.sisobobo.athena.plugin.utils;

import java.io.*;


public class FileUtil {

    public static String JAVA_PACKAGE = "src.main.java.";

    /**
     * 拼接路径和包名
     *
     * @param path
     * @param packageName
     * @return
     */
    public static String joinPathAndPackage(String path, String packageName) {
        if (!path.endsWith(File.separator)) {
            path += File.separator;
        }
        path += packageName.replace(".", File.separator);
        return path;
    }
}