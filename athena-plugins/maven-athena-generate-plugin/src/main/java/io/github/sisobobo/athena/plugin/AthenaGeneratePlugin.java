package io.github.sisobobo.athena.plugin;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import io.github.sisobobo.athena.plugin.model.Condition;
import io.github.sisobobo.athena.plugin.model.Db;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.project.MavenProject;

import java.util.List;

@Mojo(name = "all")
public class AthenaGeneratePlugin extends AbstractPlugin {

    @Override
    protected String suffix() {
        return null;
    }

    @Override
    protected void generate(MavenProject project, Db db, Condition condition, List<String> warnings) throws Exception {

    }

    @Override
    public void execute(MavenProject project, Db db, Condition condition) throws Exception {
        ClassPath classPath = ClassPath.from(AthenaGeneratePlugin.class.getClassLoader());
        String packageName = "io.github.sisobobo.athena.plugin.factory";
        ImmutableSet<ClassPath.ClassInfo> classes = classPath.getTopLevelClassesRecursive(packageName);
        for (ClassPath.ClassInfo info : classes) {
            Class clazz = info.load();
            if (AbstractPlugin.class.isAssignableFrom(clazz)) {
                AbstractPlugin plugin = (AbstractPlugin) clazz.newInstance();
                plugin.execute(project, db, condition);
            }
        }
    }
}
