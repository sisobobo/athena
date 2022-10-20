package io.github.sisobobo.athena.plugin.utils;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import io.github.sisobobo.athena.plugin.mojos.AllGenerateMojo;
import io.github.sisobobo.athena.plugin.AbstractBaseMojo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum GeneratorMojoHolder {

    INSTANCE;

    private static final Logger log = LoggerFactory.getLogger(GeneratorMojoHolder.class);

    private final Map<String, AbstractBaseMojo> PLUGIN_MAP = new ConcurrentHashMap<>(8);

    private GeneratorMojoHolder() {
        try {
            ClassPath classPath = ClassPath.from(AbstractBaseMojo.class.getClassLoader());
            String packageName = AllGenerateMojo.class.getPackage().getName();
            ImmutableSet<ClassPath.ClassInfo> classes = classPath.getTopLevelClasses(packageName);
            for (ClassPath.ClassInfo info : classes) {
                Class clazz = info.load();
                if (AbstractBaseMojo.class.isAssignableFrom(clazz)) {
                    AbstractBaseMojo plugin = (AbstractBaseMojo) clazz.newInstance();
                    if (StringUtils.isNotBlank(plugin.suffix())) {
                        PLUGIN_MAP.put(plugin.suffix(), plugin);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public AbstractBaseMojo get(String key) {
        return PLUGIN_MAP.get(key);
    }

}
