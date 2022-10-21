package io.github.sisobobo.athena.plugin.enums;

import org.apache.commons.lang3.StringUtils;

public enum ModuleEnum {

    DTO("pojo.vm", "DTO", ".api.dto", "io.github.sisobobo.athena.dto.DTO"),
    SERVICE("service.vm", "Service", ".api", ""),
    SERVICE_IMPL_MYBATIS("serviceImpl-mybatis.vm", "ServiceImpl", ".service", ""),
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
