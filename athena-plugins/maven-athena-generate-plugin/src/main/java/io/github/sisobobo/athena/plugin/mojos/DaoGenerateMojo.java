package io.github.sisobobo.athena.plugin.mojos;

import io.github.sisobobo.athena.plugin.enums.ModuleEnum;
import io.github.sisobobo.athena.plugin.AbstractBaseMojo;
import io.github.sisobobo.athena.plugin.enums.OrmEnum;
import io.github.sisobobo.athena.plugin.utils.MybatisGenerator;
import org.apache.maven.plugins.annotations.Mojo;
import java.util.List;
import java.util.Optional;


@Mojo(name = "dao")
public class DaoGenerateMojo extends AbstractBaseMojo {

    @Override
    public String suffix() {
        return "-dao";
    }

    @Override
    protected List<ModuleEnum> modules() {
        return null;
    }

    @Override
    public void executeGenerate() throws Exception {
        OrmEnum ormEnum = Optional.ofNullable(OrmEnum.keyOf(this.condition.getOrm())).orElse(OrmEnum.MYBATIS);
        if (ormEnum == OrmEnum.MYBATIS) {
            MybatisGenerator.start(this.db, this.condition, this.project, this.warnings);
        } else if (ormEnum == OrmEnum.MYBATIS_PLUS) {
            this.warnings.add(ormEnum + "暂未实现");
        }
    }
}
