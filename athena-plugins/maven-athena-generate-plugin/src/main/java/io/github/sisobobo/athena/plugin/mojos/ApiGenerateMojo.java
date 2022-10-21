package io.github.sisobobo.athena.plugin.mojos;

import io.github.sisobobo.athena.plugin.enums.ModuleEnum;
import io.github.sisobobo.athena.plugin.AbstractBaseMojo;
import org.apache.maven.plugins.annotations.Mojo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Mojo(name = "api")
public class ApiGenerateMojo extends AbstractBaseMojo {

    @Override
    public String suffix() {
        return "-api";
    }

    @Override
    protected List<ModuleEnum> modules() {
        List<ModuleEnum> list = new ArrayList<>();
        list.addAll(Arrays.asList(ModuleEnum.DTO, ModuleEnum.SERVICE));
        if (condition.isCommand()) {
            list.add(ModuleEnum.COMMAND);
        }
        if (condition.isQuery()) {
            list.add(ModuleEnum.QUERY);
        }
        return list;
    }

}
