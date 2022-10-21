package io.github.sisobobo.athena.plugin.model;

import java.util.List;

public class Table implements Cloneable {

    private String tableName;

    private List<String> packages;

    private List<Field> fields;

    private String createTime;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<String> getPackages() {
        return packages;
    }

    public void setPackages(List<String> packages) {
        this.packages = packages;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Model{" +
                "tableName='" + tableName + '\'' +
                ", packages=" + packages +
                ", fields=" + fields +
                ", createTime='" + createTime + '\'' +
                '}';
    }

    public static class Field {

        public Field(String javaType, String name, String remarks) {
            this.javaType = javaType;
            this.name = name;
            this.remarks = remarks;
        }

        private String javaType;

        private String name;

        private String remarks;

        private boolean primaryKey;

        public String getJavaType() {
            return javaType;
        }

        public void setJavaType(String javaType) {
            this.javaType = javaType;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public boolean isPrimaryKey() {
            return primaryKey;
        }

        public void setPrimaryKey(boolean primaryKey) {
            this.primaryKey = primaryKey;
        }

        @Override
        public String toString() {
            return "Field{" +
                    "javaType='" + javaType + '\'' +
                    ", name='" + name + '\'' +
                    ", remarks='" + remarks + '\'' +
                    ", primaryKey=" + primaryKey +
                    '}';
        }
    }

}
