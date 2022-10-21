package io.github.sisobobo.athena.plugin.mojos;

import io.github.sisobobo.athena.plugin.enums.ModuleEnum;
import io.github.sisobobo.athena.plugin.AbstractBaseMojo;
import io.github.sisobobo.athena.plugin.enums.OrmEnum;
import org.apache.maven.plugins.annotations.Mojo;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Mojo(name = "service")
public class ServiceGenerateMojo extends AbstractBaseMojo {

    @Override
    public String suffix() {
        return "-service";
    }

    @Override
    protected List<ModuleEnum> modules() {
        OrmEnum ormEnum = Optional.ofNullable(OrmEnum.keyOf(this.condition.getOrm())).orElse(OrmEnum.MYBATIS);
        if (ormEnum == OrmEnum.MYBATIS) {
            return Arrays.asList(ModuleEnum.SERVICE_IMPL_MYBATIS);
        } else if (ormEnum == OrmEnum.MYBATIS_PLUS) {
            this.warnings.add(ormEnum + "暂未实现");
        }
        return null;
    }

}
