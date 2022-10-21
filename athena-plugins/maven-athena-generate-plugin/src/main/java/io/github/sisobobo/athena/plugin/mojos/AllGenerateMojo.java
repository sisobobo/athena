package io.github.sisobobo.athena.plugin.mojos;

import io.github.sisobobo.athena.plugin.enums.ModuleEnum;
import io.github.sisobobo.athena.plugin.AbstractBaseMojo;
import io.github.sisobobo.athena.plugin.model.Db;
import io.github.sisobobo.athena.plugin.model.Table;
import io.github.sisobobo.athena.plugin.utils.GeneratorMojoHolder;
import io.github.sisobobo.athena.plugin.utils.JdbcUtil;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.project.MavenProject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Mojo(name = "all")
public class AllGenerateMojo extends AbstractBaseMojo {

    @Override
    public String suffix() {
        return null;
    }

    @Override
    protected List<ModuleEnum> modules() {
        return null;
    }

    @Override
    public void generate() throws Exception {
        String key = getKey(this.project);
        AbstractBaseMojo plugin = GeneratorMojoHolder.INSTANCE.get(key);
        if (Objects.isNull(plugin)) {
            return;
        }
        plugin.setParams(this.db, this.condition, this.project);
        plugin.executeGenerate();
    }

    private String getKey(MavenProject project) {
        return project.getArtifactId().replace(project.getParent().getArtifactId(), "");
    }

    public static void main(String[] args) throws SQLException {
        Db db = new Db();
        db.setUrl("jdbc:mysql://localhost:3306/athena_upms");
        db.setUsername("root");
        db.setPassword("123456");
        db.setTables("upms_role");
        List<Table> models = JdbcUtil.getModels(db, new ArrayList<>());
        for (Table table : models) {
            System.out.println();
        }
    }

}
