package io.github.sisobobo.athena.plugin.generator.api;

import io.github.sisobobo.athena.plugin.generator.AbstractPlugin;
import io.github.sisobobo.athena.plugin.model.Condition;
import io.github.sisobobo.athena.plugin.model.Db;
import io.github.sisobobo.athena.plugin.model.Model;
import io.github.sisobobo.athena.plugin.utils.GenerateUtil;
import io.github.sisobobo.athena.plugin.utils.JdbcUtil;
import io.github.sisobobo.athena.plugin.utils.Velocity;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.project.MavenProject;
import org.apache.velocity.VelocityContext;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Mojo(name = "api")
public class ApiGeneratePlugin extends AbstractPlugin {

    @Override
    protected String suffix() {
        return "-api";
    }

    @Override
    protected void generate(MavenProject project, Db db, Condition condition) throws Exception {
        List<String> warnings = new ArrayList<>();
        List<Model> models = JdbcUtil.getModels(db, warnings);
        generateDto(models, project);
        generateService(models, project);
    }

    private void generateDto(List<Model> models, MavenProject project) throws Exception {
        String packageName = project.getGroupId() + ".api.dto";
        List<Model> list = JdbcUtil.modifyModelName(models, "DTO");
        GenerateUtil.generateJavaModelFiles(list, project.getBasedir().getPath(), packageName, isOverwrite());
    }

    private void generateService(List<Model> models, MavenProject project) throws Exception {
        String packageName = project.getGroupId() + ".api";
        GenerateUtil.generateServiceFiles(models, project.getGroupId(), project.getBasedir().getPath(), packageName, isOverwrite());
    }
}
