package io.github.sisobobo.plugin.mybatis.generator;

import io.github.sisobobo.plugin.mybatis.generator.utils.MybatisGenerator;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;


@Mojo(name = "generate")
public class MybatisGeneratorPlugin extends AbstractMojo {

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

    @Parameter(property = "overwrite", defaultValue = "true" ,readonly = true)
    private Boolean overwrite;

    @Override
    public void execute() {
        MybatisGenerator.start(url, username, password, prefix, overwrite, driver, project);
    }

}
