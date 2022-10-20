package io.github.sisobobo.athena.plugin.mojos;

import io.github.sisobobo.athena.plugin.enums.ModuleEnum;
import io.github.sisobobo.athena.plugin.AbstractBaseMojo;
import org.apache.maven.plugins.annotations.Mojo;
import java.util.ArrayList;
import java.util.List;

@Mojo(name = "web")
public class WebGenerateMojo extends AbstractBaseMojo {

    @Override
    public String suffix() {
        return "-web";
    }

    @Override
    protected List<ModuleEnum> modules() {
        List<ModuleEnum> list = new ArrayList<>();
        if (this.condition.isCreateVo()) {
            list.add(ModuleEnum.VO);
        }
        if (this.condition.isCreateController()) {
            list.add(ModuleEnum.CONTROLLER);
        }
        return list;
    }


}
