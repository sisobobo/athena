package io.github.sisobobo.athena.plugin.model;

import io.github.sisobobo.athena.plugin.enums.OrmEnum;

public class Condition {

    private boolean overwrite = false;

    private OrmEnum orm = OrmEnum.MYBATIS;

    private boolean createVo = false;

    private boolean createController = false;

    public boolean isOverwrite() {
        return overwrite;
    }

    public void setOverwrite(boolean overwrite) {
        this.overwrite = overwrite;
    }

    public OrmEnum getOrm() {
        return orm;
    }

    public void setOrm(OrmEnum orm) {
        this.orm = orm;
    }

    public boolean isCreateVo() {
        return createVo;
    }

    public void setCreateVo(boolean createVo) {
        this.createVo = createVo;
    }

    public boolean isCreateController() {
        return createController;
    }

    public void setCreateController(boolean createController) {
        this.createController = createController;
    }

    @Override
    public String toString() {
        return "Condition{" +
                "overwrite=" + overwrite +
                ", orm=" + orm +
                ", createVo=" + createVo +
                ", createController=" + createController +
                '}';
    }
}
