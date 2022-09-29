package io.github.sisobobo.plugin.mybatis.generator;

import io.github.sisobobo.plugin.mybatis.generator.utils.MybatisGenerator;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;


@Mojo(name = "generate")
public class MybatisGeneratorPlugin extends AbstractMojo {

    @Parameter(property = "project", required = true, readonly = true)
    private MavenProject project;

    @Parameter(property = "url", required = true, readonly = true)
    private String url;

    @Parameter(property = "driver", required = false, readonly = true)
    private String driver;

    @Parameter(property = "username", required = true, readonly = true)
    private String username;

    @Parameter(property = "password", required = true, readonly = true)
    private String password;

    @Parameter(property = "prefix", required = false, readonly = true)
    private String prefix;

    @Parameter(property = "overwrite", required = false, readonly = true)
    private boolean overwrite = true;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        MybatisGenerator.start(url, username, password, prefix, overwrite, driver, project);
    }

}
