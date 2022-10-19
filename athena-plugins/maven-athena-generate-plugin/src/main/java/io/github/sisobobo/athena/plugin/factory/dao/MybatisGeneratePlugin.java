package io.github.sisobobo.athena.plugin.factory.dao;

import io.github.sisobobo.athena.plugin.AbstractPlugin;
import io.github.sisobobo.athena.plugin.model.Condition;
import io.github.sisobobo.athena.plugin.model.Db;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.project.MavenProject;

import java.util.List;


@Mojo(name = "dao-mybatis")
public class MybatisGeneratePlugin extends AbstractPlugin {

    @Override
    protected String suffix() {
        return "-dao";
    }

    @Override
    protected void generate(MavenProject project, Db db, Condition condition, List<String> warnings) throws Exception {
        MybatisGenerator.start(db, condition, project, warnings);
    }

}
