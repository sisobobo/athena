package io.github.sisobobo.plugin.mybatis.generator;

import io.github.sisobobo.plugin.mybatis.generator.utils.MybatisGenerator;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Mojo(name = "generate")
public class MybatisGeneratorPlugin extends AbstractMojo {

    private static final Logger log = LoggerFactory.getLogger(MybatisGeneratorPlugin.class);

    @Parameter(property = "project", required = true, readonly = true)
    private MavenProject project;

    @Parameter(property = "url", required = true, readonly = true)
    private String url;

    @Parameter(property = "driver", readonly = true)
    private String driver;

    @Parameter(property = "username", required = true, readonly = true)
    private String username;

    @Parameter(property = "password", required = true, readonly = true)
    private String password;

    @Parameter(property = "prefix", readonly = true)
    private String prefix;

    @Parameter(property = "overwrite", defaultValue = "true", readonly = true)
    private Boolean overwrite;

    @Parameter(property = "useLombok", defaultValue = "true", readonly = true)
    private Boolean useLombok;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        log.info("Parameter: url , value:{}" , url);
        log.info("Parameter: driver , value:{}" , driver);
        log.info("Parameter: username , value:{}" , username);
        log.info("Parameter: password , value:{}" , password);
        log.info("Parameter: prefix , value:{}" , prefix);
        log.info("Parameter: overwrite , value:{}" , overwrite);
        log.info("Parameter: useLombok , value:{}" , useLombok);
        MybatisGenerator.start(url, username, password, prefix, overwrite, driver, project, useLombok);
    }
}
