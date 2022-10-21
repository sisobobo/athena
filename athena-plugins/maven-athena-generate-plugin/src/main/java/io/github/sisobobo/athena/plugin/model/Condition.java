package io.github.sisobobo.athena.plugin.model;

public class Condition {

    private boolean overwrite = false;

    private String orm = "mybatis";

    private boolean vo = false;

    private boolean controller = true;

    public boolean isOverwrite() {
        return overwrite;
    }

    public void setOverwrite(boolean overwrite) {
        this.overwrite = overwrite;
    }

    public String getOrm() {
        return orm;
    }

    public void setOrm(String orm) {
        this.orm = orm;
    }

    public boolean isVo() {
        return vo;
    }

    public void setVo(boolean vo) {
        this.vo = vo;
    }

    public boolean isController() {
        return controller;
    }

    public void setController(boolean controller) {
        this.controller = controller;
    }

    @Override
    public String toString() {
        return "Condition{" +
                "overwrite=" + overwrite +
                ", orm=" + orm +
                ", vo=" + vo +
                ", controller=" + controller +
                '}';
    }
}
