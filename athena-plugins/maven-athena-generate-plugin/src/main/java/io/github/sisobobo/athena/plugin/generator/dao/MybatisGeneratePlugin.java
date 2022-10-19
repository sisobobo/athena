package io.github.sisobobo.athena.plugin.generator.dao;

import io.github.sisobobo.athena.plugin.generator.AbstractPlugin;
import io.github.sisobobo.athena.plugin.model.Condition;
import io.github.sisobobo.athena.plugin.model.Db;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.project.MavenProject;


@Mojo(name = "dao-mybatis")
public class MybatisGeneratePlugin extends AbstractPlugin {

    @Override
    protected String suffix() {
        return "-dao";
    }

    @Override
    protected void generate(MavenProject project, Db db, Condition condition) {
        MybatisGenerator.start(db, condition, project);
    }


}
