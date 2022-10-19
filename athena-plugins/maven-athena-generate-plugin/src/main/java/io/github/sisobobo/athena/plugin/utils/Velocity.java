package io.github.sisobobo.athena.plugin.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class Velocity {

    private static final VelocityEngine VELOCITY_ENGINE;

    private static final String VM_PATH = "/template";

    static {
        // 初始化模板引擎
        VELOCITY_ENGINE = new VelocityEngine();
        VELOCITY_ENGINE.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        VELOCITY_ENGINE.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        VELOCITY_ENGINE.init();
    }

    public static File makeFile(String name, String outFile, VelocityContext velocityContext, boolean overwrite) throws IOException {
        FileWriter fileWriter = null;
        File file = new File(outFile);
        try {
            if (file.exists() && !overwrite) {
                return file;
            }
            File parentFile = file.getParentFile();
            if (parentFile.exists()) {
                file.createNewFile();
            } else {
                parentFile.mkdirs();
                file.createNewFile();
            }
            Template template = findTemplate(name);
            fileWriter = new FileWriter(file);
            template.merge(velocityContext, fileWriter);
        } catch (Exception e) {
            throw new IOException(e);
        } finally {
            if (fileWriter != null) {
                fileWriter.close();
            }
        }
        return file;
    }

    private static Template findTemplate(String name) {
        Template template = null;
        if (StringUtils.isNotBlank(name)) {
            String path = VM_PATH + "/" + name;
            if (!name.endsWith(".vm")) {
                path += ".vm";
            }
            // 获取模板文件
            template = VELOCITY_ENGINE.getTemplate(path, "UTF-8");
        }
        return template;
    }
}