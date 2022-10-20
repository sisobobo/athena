package io.github.sisobobo.athena.plugin.mojos;

import io.github.sisobobo.athena.plugin.enums.ModuleEnum;
import io.github.sisobobo.athena.plugin.AbstractBaseMojo;
import org.apache.maven.plugins.annotations.Mojo;
import java.util.Arrays;
import java.util.List;

@Mojo(name = "service")
public class ServiceGenerateMojo extends AbstractBaseMojo {

    @Override
    public String suffix() {
        return "-service";
    }

    @Override
    protected List<ModuleEnum> modules() {
        return Arrays.asList(ModuleEnum.SERVICE_IMPL);
    }

}
