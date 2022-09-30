package io.github.sisobobo.athena.plugin.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Jdbc {

    private String protocol;

    private String host;

    private String port;

    private String dbName;

    public Jdbc(String url) {
        String regex = "jdbc:(?<protocol>\\w+):.*((//)|@)(?<host>.+):(?<port>\\d+)(/|(;DatabaseName=)|:)(?<dbName>\\w+)\\??.*";
        Pattern pattern = Pattern.compile(regex);
        Matcher m = pattern.matcher(url);
        if (m.find()) {
            this.protocol = m.group("protocol");
            this.host = m.group("host");
            this.port = m.group("port");
            this.dbName = m.group("dbName");
        }
    }

    public String getProtocol() {
        return protocol;
    }

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }

    public String getDbName() {
        return dbName;
    }

    @Override
    public String toString() {
        return "Jdbc{" +
                "protocol='" + protocol + '\'' +
                ", host='" + host + '\'' +
                ", port='" + port + '\'' +
                ", dbName='" + dbName + '\'' +
                '}';
    }
}
