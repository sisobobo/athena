package io.github.sisobobo.athena.plugin.enums;

import org.apache.commons.lang3.StringUtils;

public enum ModuleEnum {
    //api层
    DTO("pojo.vm", "DTO", ".api.dto", "io.github.sisobobo.athena.dto.DTO"),
    QUERY("pojo.vm", "Query", ".api.dto.query", "io.github.sisobobo.athena.dto.Query"),
    PAGE_QUERY("pojo.vm", "PageQuery", ".api.dto.query", "io.github.sisobobo.athena.dto.PageQuery"),
    COMMAND("pojo.vm", "Cmd", ".api.dto.command", "io.github.sisobobo.athena.dto.Command"),
    SERVICE("service.vm", "Service", ".api", ""),
    //service层
    SERVICE_IMPL_MYBATIS("serviceImpl-mybatis.vm", "ServiceImpl", ".service", ""),
    //web层
    VO("pojo.vm", "VO", ".web.vo", ""),
    CONTROLLER("controller.vm", "Controller", ".web", ""),


    ;
    private String template;

    private String fileSuffix;

    private String packageSuffix;

    private String superClassName;

    private ModuleEnum(String template, String fileSuffix, String packageSuffix, String superClassName) {
        this.template = template;
        this.fileSuffix = fileSuffix;
        this.packageSuffix = packageSuffix;
        this.superClassName = superClassName;
    }

    public String getTemplate() {
        return template;
    }

    public String getFileSuffix() {
        return fileSuffix;
    }

    public String getPackageName(String groupId) {
        return groupId + this.packageSuffix;
    }

    public String getSuperClassName() {
        return superClassName;
    }

    public String getSuperClassSimpleName() {
        if (StringUtils.isBlank(this.superClassName)) {
            return "";
        }
        String[] split = this.superClassName.split("\\.");
        return split[split.length - 1];
    }

}
