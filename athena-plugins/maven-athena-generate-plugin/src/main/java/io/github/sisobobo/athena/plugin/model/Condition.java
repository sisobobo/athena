package io.github.sisobobo.athena.plugin.model;

public class Condition {

    private Boolean overwrite;

    @Override
    public String toString() {
        return "Condition{" +
                "overwrite=" + overwrite +
                '}';
    }

    public Boolean getOverwrite() {
        return overwrite;
    }

    public void setOverwrite(Boolean overwrite) {
        this.overwrite = overwrite;
    }

}
