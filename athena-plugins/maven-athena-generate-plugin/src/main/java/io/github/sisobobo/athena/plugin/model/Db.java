package io.github.sisobobo.athena.plugin.model;

import org.apache.commons.lang3.StringUtils;

public class Db {

    private String url;

    private String driver;

    private String username;

    private String password;

    private String prefix;

    private String tables;

    public Db(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public Db() {

    }


    @Override
    public String toString() {
        return "Db{" +
                "url='" + url + '\'' +
                ", driver='" + driver + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", prefix='" + prefix + '\'' +
                ", tables='" + tables + '\'' +
                '}';
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDriver() {
        if (StringUtils.isBlank(this.driver)) {
            return "com.mysql.cj.jdbc.Driver";
        }
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getTables() {
        return tables;
    }

    public void setTables(String tables) {
        this.tables = tables;
    }
}
