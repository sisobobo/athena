package io.github.sisobobo.athena.plugin.utils;

import com.google.common.base.CaseFormat;
import io.github.sisobobo.athena.plugin.enums.ModuleEnum;
import io.github.sisobobo.athena.plugin.model.Condition;
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
            putBaseContext(context, model, suffix, groupId);
            File file = generateJavaFile(template, baseDir, context, overwrite);
            files.add(file);
        }
        return files;
    }

    private static void putBaseContext(VelocityContext context, Table table, String suffix, String groupId) {
        String modelName = lineToHump(table.getTableName());
        context.put("modelName", modelName);
        context.put("fileName", modelName + suffix);
        context.put("lowModelName", lowerFirstCapse(modelName));
        context.put("mapping", nameToMapping(table.getTableName()));
        context.put("model", table);
        putCondition(context, modelName, groupId);
    }

    private static void putCondition(VelocityContext context, String modelName, String groupId) {
        Condition condition = (Condition) context.get("condition");
        String command = ModuleEnum.COMMAND.getSuperClassSimpleName();
        String commandPkg = ModuleEnum.COMMAND.getSuperClassName();
        if (condition.isCommand()) {
            command = modelName + ModuleEnum.COMMAND.getFileSuffix();
            commandPkg = ModuleEnum.COMMAND.getPackageName(groupId) + "." + command;
        }
        String query = ModuleEnum.QUERY.getSuperClassSimpleName();
        String queryPkg = ModuleEnum.QUERY.getSuperClassName();
        String pageQuery = ModuleEnum.PAGE_QUERY.getSuperClassSimpleName();
        String pageQueryPkg = ModuleEnum.PAGE_QUERY.getSuperClassName();
        if (condition.isQuery()) {
            query = modelName + ModuleEnum.QUERY.getFileSuffix();
            queryPkg = ModuleEnum.QUERY.getPackageName(groupId) + "." + query;
            pageQuery = modelName + ModuleEnum.PAGE_QUERY.getFileSuffix();
            pageQueryPkg = ModuleEnum.PAGE_QUERY.getPackageName(groupId) + "." + pageQuery;
        }
        String vo = modelName + ModuleEnum.DTO.getFileSuffix();
        String voPkg = ModuleEnum.DTO.getPackageName(groupId) + "." + vo;
//        if(condition.isVo()){
//            vo = modelName + ModuleEnum.VO.getFileSuffix();
//            voPkg = ModuleEnum.VO.getPackageName(groupId) + "." + vo;
//        }
        context.put("vo", vo);
        context.put("voPkg", voPkg);
        context.put("command", command);
        context.put("commandPkg", commandPkg);
        context.put("query", query);
        context.put("queryPkg", queryPkg);
        context.put("pageQuery", pageQuery);
        context.put("pageQueryPkg", pageQueryPkg);
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
