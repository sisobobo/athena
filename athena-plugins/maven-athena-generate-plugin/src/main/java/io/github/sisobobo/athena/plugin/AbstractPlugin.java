package io.github.sisobobo.athena.plugin;

import io.github.sisobobo.athena.plugin.model.Db;
import io.github.sisobobo.athena.plugin.model.Condition;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
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

    protected abstract void generate(MavenProject project, Db db, Condition condition, List<String> warnings) throws Exception;


    public void execute(MavenProject project, Db db, Condition condition) throws Exception {
        String artifactId = project.getArtifactId();
        if (artifactId.endsWith(suffix())) {
            List<String> warnings = new ArrayList<>();
            generate(project, db, condition, warnings);
            warnings.forEach(log::error);
        }
    }

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        log.info("Parameter: db info , {}", db.toString());
        log.info("Parameter: condition info , {}", condition.toString());
        try {
            execute(project, db, condition);
        } catch (Exception e) {
            throw new MojoExecutionException("生成代码出错", e);
        }
    }


}
