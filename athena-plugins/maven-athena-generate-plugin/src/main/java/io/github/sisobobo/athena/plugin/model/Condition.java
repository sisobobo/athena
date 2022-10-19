package io.github.sisobobo.athena.plugin.model;

public class Condition {

    private Boolean overwrite;

    private String orm ;

    @Override
    public String toString() {
        return "Condition{" +
                "overwrite=" + overwrite +
                ", orm='" + orm + '\'' +
                '}';
    }

    public String getOrm() {
        return orm;
    }

    public void setOrm(String orm) {
        this.orm = orm;
    }

    public Boolean getOverwrite() {
        return overwrite;
    }

    public void setOverwrite(Boolean overwrite) {
        this.overwrite = overwrite;
    }

}
