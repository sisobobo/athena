package io.github.sisobobo.athena.plugin.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.Locale;

public enum OrmEnum {

    MYBATIS("mybatis"),

    MYBATIS_PLUS("mybatis-plus"),

    ;

    private String key;

    private OrmEnum(String key) {
        this.key = key;
    }

    public static OrmEnum keyOf(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        String lowKey = key.toLowerCase(Locale.ROOT);
        for (OrmEnum ormEnum : values()) {
            if (lowKey.equals(ormEnum.key)) {
                return ormEnum;
            }
        }
        return null;
    }


}
