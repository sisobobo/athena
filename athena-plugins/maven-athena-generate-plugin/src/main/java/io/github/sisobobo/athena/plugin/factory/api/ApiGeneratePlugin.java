package io.github.sisobobo.athena.plugin.factory.api;

import io.github.sisobobo.athena.plugin.AbstractPlugin;
import io.github.sisobobo.athena.plugin.model.Condition;
import io.github.sisobobo.athena.plugin.model.Db;
import io.github.sisobobo.athena.plugin.model.Model;
import io.github.sisobobo.athena.plugin.utils.GenerateUtil;
import io.github.sisobobo.athena.plugin.utils.JdbcUtil;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.project.MavenProject;

import java.util.List;


@Mojo(name = "api")
public class ApiGeneratePlugin extends AbstractPlugin {

    @Override
    protected String suffix() {
        return "-api";
    }

    @Override
    protected void generate(MavenProject project, Db db, Condition condition, List<String> warnings) throws Exception {
        List<Model> models = JdbcUtil.getModels(db, warnings);
        boolean overwrite = GenerateUtil.isOverwrite(condition);
        generateDto(models, project, overwrite);
        generateService(models, project, overwrite);
    }

    private void generateDto(List<Model> models, MavenProject project, boolean overwrite) throws Exception {
        String packageName = project.getGroupId() + ".api.dto";
        List<Model> list = JdbcUtil.modifyModelName(models, "DTO");
        GenerateUtil.generateJavaModelFiles(list, project.getBasedir().getPath(), packageName, overwrite);
    }

    private void generateService(List<Model> models, MavenProject project, boolean overwrite) throws Exception {
        String packageName = project.getGroupId() + ".api";
        GenerateUtil.generateServiceFiles(models, project.getGroupId(), project.getBasedir().getPath(), packageName, overwrite);
    }
}
