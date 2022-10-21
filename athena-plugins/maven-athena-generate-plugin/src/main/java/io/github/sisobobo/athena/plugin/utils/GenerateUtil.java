package io.github.sisobobo.athena.plugin.utils;

import com.google.common.base.CaseFormat;
import io.github.sisobobo.athena.plugin.model.Table;
import org.apache.velocity.VelocityContext;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GenerateUtil {

    private static String JAVA_PACKAGE = "src.main.java.";

    public static List<File> generateJavaFiles(String template, String suffix, List<Table> models, VelocityContext context, String baseDir, String groupId, String packageName, boolean overwrite) throws IOException {
        List<File> files = new ArrayList<>(models.size());
        suffix = Optional.ofNullable(suffix).orElse("");
        context.put("package", packageName);
        context.put("groupId", groupId);
        for (Table model : models) {
            putBaseContext(context, model, suffix);
            File file = generateJavaFile(template, baseDir, context, overwrite);
            files.add(file);
        }
        return files;
    }

    private static void putBaseContext(VelocityContext context, Table table, String suffix) {
        String modelName = lineToHump(table.getTableName());
        context.put("modelName", modelName);
        context.put("fileName", modelName + suffix);
        context.put("lowModelName", lowerFirstCapse(modelName));
        context.put("mapping", nameToMapping(table.getTableName()));
        context.put("model", table);
    }

    private static File generateJavaFile(String template, String baseDir, VelocityContext context, boolean overwrite) throws IOException {
        String fileName = (String) context.get("fileName");
        String packageName = (String) context.get("package");
        String path = joinPathAndPackage(baseDir, JAVA_PACKAGE + packageName);
        String filePath = path + File.separator + fileName + ".java";
        File file = Velocity.makeFile(template, filePath, context, overwrite);
        return file;
    }

    /**
     * 下划线转驼峰
     */
    public static String lineToHump(String str) {
        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, str);
    }

    /**
     * 驼峰第一个字母小写
     */
    private static String lowerFirstCapse(String str) {
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, str);
    }

    /**
     * 转换Mapping名称
     *
     * @param name
     * @return
     */
    private static String nameToMapping(String name) {
        return "/" + name.replace("_", "/");
    }

    /**
     * 拼接路径和包名
     *
     * @param path
     * @param packageName
     * @return
     */
    private static String joinPathAndPackage(String path, String packageName) {
        if (!path.endsWith(File.separator)) {
            path += File.separator;
        }
        path += packageName.replace(".", File.separator);
        return path;
    }


}
