package io.github.sisobobo.athena.plugin.generator;

import io.github.sisobobo.athena.plugin.model.Db;
import io.github.sisobobo.athena.plugin.model.Condition;
import io.github.sisobobo.athena.plugin.model.Model;
import io.github.sisobobo.athena.plugin.utils.GenerateUtil;
import io.github.sisobobo.athena.plugin.utils.JdbcUtil;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public abstract class AbstractPlugin extends AbstractMojo {

    private static final Logger log = LoggerFactory.getLogger(AbstractPlugin.class);

    @Parameter(property = "project", required = true, readonly = true)
    private MavenProject project;

    @Parameter(property = "db", required = true, readonly = true)
    private Db db;

    @Parameter(property = "condition", readonly = true)
    private Condition condition;

    protected abstract String suffix();

    protected abstract void generate(MavenProject project, Db db, Condition condition ) throws Exception;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        String artifactId = project.getArtifactId();
        if (artifactId.endsWith(suffix())) {
            log.info("Parameter: db info , {}", db.toString());
            log.info("Parameter: condition info , {}", condition.toString());
            try {
                generate(project, db, condition);
            } catch (Exception e) {
                throw new MojoExecutionException("数据库错误", e);
            }
        }
    }

    protected boolean isOverwrite() {
        return GenerateUtil.isOverwrite(condition);
    }

}
