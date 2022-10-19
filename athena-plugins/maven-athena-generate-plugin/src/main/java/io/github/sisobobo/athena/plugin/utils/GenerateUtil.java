package io.github.sisobobo.athena.plugin.utils;

import io.github.sisobobo.athena.plugin.model.Condition;
import io.github.sisobobo.athena.plugin.model.Model;
import org.apache.velocity.VelocityContext;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GenerateUtil {

    private static final String MODEL_TEMPLATE = "model.vm";

    private static final String SERVICE_TEMPLATE = "service.vm";

    private static final String SERVICE_IMPL_TEMPLATE = "serviceImpl.vm";

    public static List<File> generateJavaModelFiles(List<Model> models, String baseDir, String packageName, boolean overwrite) throws IOException {
        List<File> files = new ArrayList<>(models.size());
        VelocityContext context = new VelocityContext();
        for (Model model : models) {
            context.put("model", model);
            File file = generateJavaFile(MODEL_TEMPLATE, model, baseDir, packageName, context, overwrite);
            files.add(file);
        }
        return files;
    }

    public static List<File> generateServiceFiles(List<Model> models, String groupId, String baseDir, String packageName, boolean overwrite) throws IOException {
        List<File> files = new ArrayList<>(models.size());
        VelocityContext context = new VelocityContext();
        for (Model model : models) {
            context.put("groupId", groupId);
            context.put("modelName", model.getModelName());
            model.setModelName(model.getModelName() + "Service");
            File file = generateJavaFile(SERVICE_TEMPLATE, model, baseDir, packageName, context, overwrite);
            files.add(file);
        }
        return files;
    }

    public static List<File> generateServiceImplFiles(List<Model> models, String groupId, String baseDir, String packageName, boolean overwrite) throws IOException {
        List<File> files = new ArrayList<>(models.size());
        VelocityContext context = new VelocityContext();
        for (Model model : models) {
            context.put("groupId", groupId);
            context.put("modelName", model.getModelName());
            model.setModelName(model.getModelName() + "ServiceImpl");
            File file = generateJavaFile(SERVICE_IMPL_TEMPLATE, model, baseDir, packageName, context, overwrite);
            files.add(file);
        }
        return files;
    }

    private static File generateJavaFile(String template, Model model, String baseDir, String packageName, VelocityContext context, boolean overwrite) throws IOException {
        context.put("package", packageName);
        String path = FileUtil.joinPathAndPackage(baseDir, FileUtil.JAVA_PACKAGE + packageName);
        String fileName = path + File.separator + model.getModelName() + ".java";
        File file = Velocity.makeFile(template, fileName, context, overwrite);
        return file;
    }

    /**
     * 是否覆盖文件
     *
     * @param condition
     * @return
     */
    public static boolean isOverwrite(Condition condition) {
        return Objects.isNull(condition) || Objects.isNull(condition.getOverwrite()) ? false : condition.getOverwrite();
    }


}
