package io.github.sisobobo.athena.plugin.enums;

public enum ModuleEnum {

    DTO("dto.vm", "DTO", ".api.dto"),
    SERVICE("service.vm", "Service", ".api"),
    SERVICE_IMPL("serviceImpl.vm", "ServiceImpl", ".service"),
    VO("model.vm", "VO", ".web.vo"),
    QUERY("model.vm", "VO", ".web.vo"),
    CONTROLLER("controller.vm", "Controller", ".web");

    private String template;

    private String fileSuffix;

    private String packageSuffix;

    private ModuleEnum(String template, String fileSuffix, String packageSuffix) {
        this.template = template;
        this.fileSuffix = fileSuffix;
        this.packageSuffix = packageSuffix;
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
}
