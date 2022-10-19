package io.github.sisobobo.athena.plugin.generator.service;

import io.github.sisobobo.athena.plugin.generator.AbstractPlugin;
import io.github.sisobobo.athena.plugin.model.Condition;
import io.github.sisobobo.athena.plugin.model.Db;
import io.github.sisobobo.athena.plugin.model.Model;
import io.github.sisobobo.athena.plugin.utils.GenerateUtil;
import io.github.sisobobo.athena.plugin.utils.JdbcUtil;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.project.MavenProject;

import java.util.ArrayList;
import java.util.List;

@Mojo(name = "service")
public class ServiceGeneratePlugin extends AbstractPlugin {

    @Override
    protected String suffix() {
        return "-service";
    }

    @Override
    protected void generate(MavenProject project, Db db, Condition condition) throws Exception {
        List<String> warnings = new ArrayList<>();
        List<Model> models = JdbcUtil.getModels(db, warnings);
        generateServiceImpl(models, project);
    }

    private void generateServiceImpl(List<Model> models, MavenProject project) throws Exception {
        String packageName = project.getGroupId() + ".service";
        GenerateUtil.generateServiceImplFiles(models, project.getGroupId(), project.getBasedir().getPath(), packageName, isOverwrite());
    }

}
