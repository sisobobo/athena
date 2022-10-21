package io.github.sisobobo.athena.plugin;

import io.github.sisobobo.athena.plugin.enums.ModuleEnum;
import io.github.sisobobo.athena.plugin.model.Db;
import io.github.sisobobo.athena.plugin.model.Condition;
import io.github.sisobobo.athena.plugin.model.Table;
import io.github.sisobobo.athena.plugin.utils.GenerateUtil;
import io.github.sisobobo.athena.plugin.utils.JdbcUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.apache.velocity.VelocityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

public abstract class AbstractBaseMojo extends AbstractMojo {

    private static final Logger log = LoggerFactory.getLogger(AbstractBaseMojo.class);

    @Parameter(property = "project", required = true, readonly = true)
    protected MavenProject project;

    @Parameter(property = "db", required = true, readonly = true)
    protected Db db;

    @Parameter(property = "condition", readonly = true)
    protected Condition condition;

    protected final List<String> warnings = new ArrayList<>();

    private final VelocityContext context = new VelocityContext();

    public abstract String suffix();

    protected abstract List<ModuleEnum> modules();

    @Override
    public final void execute() throws MojoExecutionException, MojoFailureException {
        if (Objects.isNull(this.project.getParent())) {
            return;
        }
        log.info("Parameter: db info , {}", this.db);
        this.condition = Optional.ofNullable(this.condition).orElse(new Condition());
        log.info("Parameter: condition info , {}", this.condition);
        try {
            generate();
        } catch (Exception e) {
            log.error("", e);
            throw new MojoExecutionException(e);
        }
    }

    protected boolean isExecute() {
        String artifactId = this.project.getArtifactId();
        return null != suffix() && artifactId.endsWith(suffix()) ? true : false;
    }

    public void executeGenerate() throws Exception {
        List<Table> tables = JdbcUtil.getModels(this.db, this.warnings);
        List<ModuleEnum> moduleEnums = Optional.ofNullable(modules()).orElse(Collections.EMPTY_LIST);
        for (ModuleEnum moduleEnum : moduleEnums) {
            boolean hasSuperClass = StringUtils.isNotBlank(moduleEnum.getSuperClassName());
            this.context.put("hasSuperClass", hasSuperClass);
            if (hasSuperClass) {
                this.context.put("superSimpleName", moduleEnum.getSuperClassSimpleName());
                this.context.put("superFullName", moduleEnum.getSuperClassName());
            }
            generateJavaFiles(moduleEnum, tables);
        }
        this.warnings.forEach(this.log::warn);
    }

    public void generate() throws Exception {
        if (isExecute()) {
            executeGenerate();
        }
    }

    private void generateJavaFiles(ModuleEnum moduleEnum, List<Table> models) throws IOException {
        String groupId = this.project.getGroupId();
        String baseDir = this.project.getBasedir().getPath();
        String packageName = moduleEnum.getPackageName(this.project.getGroupId());
        boolean overwrite = this.condition.isOverwrite();
        GenerateUtil.generateJavaFiles(moduleEnum.getTemplate(), moduleEnum.getFileSuffix(), models, this.context, baseDir, groupId, packageName, overwrite);
    }

    public void setParams(Db db, Condition condition, MavenProject project) {
        this.db = db;
        this.condition = condition;
        this.project = project;
    }
}
