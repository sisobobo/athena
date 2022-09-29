package io.github.sisobobo.plugin.utils;

import cn.hutool.core.lang.Assert;
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

    public static void makeFile(String name, File outFile, VelocityContext velocityContext) throws IOException {
        FileWriter fileWriter = null;
        try {
            Template template = findTemplate(name);
            fileWriter = new FileWriter(outFile);
            template.merge(velocityContext, fileWriter);
        } catch (Exception e) {
            throw new IOException(e);
        } finally {
            if (fileWriter != null) {
                fileWriter.close();
            }
        }

    }

    private static Template findTemplate(String name) {
        Assert.notBlank(name);
        String path = VM_PATH + "/" + name;
        if (!name.endsWith(".vm")) {
            path += ".vm";
        }
        // 获取模板文件
        Template template = VELOCITY_ENGINE.getTemplate(path , "UTF-8");
        Assert.notNull(template, "获取模板文件[" + name + "]异常");
        return template;
    }
}