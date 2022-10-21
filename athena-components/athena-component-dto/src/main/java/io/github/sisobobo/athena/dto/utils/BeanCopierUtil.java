package io.github.sisobobo.athena.dto.utils;

import org.springframework.cglib.beans.BeanCopier;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.objenesis.ObjenesisStd;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 对象拷贝工具
 */
public class BeanCopierUtil {
    private static ThreadLocal<ObjenesisStd> objenesisStdThreadLocal = ThreadLocal.withInitial(ObjenesisStd::new);
    private static ConcurrentHashMap<Class<?>, ConcurrentHashMap<Class<?>, BeanCopier>> cache = new ConcurrentHashMap<>();

    /**
     * copy对象
     * @param source
     * @param target
     * @param <T>
     * @return
     */
    public static <T> T copy(Object source, Class<T> target) {
        return copy(source, objenesisStdThreadLocal.get().newInstance(target));
    }

    private static <T> T copy(Object source, T target) {
        BeanCopier beanCopier = getCacheBeanCopier(source.getClass(), target.getClass());
        beanCopier.copy(source, target, null);
        return target;
    }

    /**
     * 拷贝数组
     * @param sources
     * @param target
     * @param <T>
     * @return
     */
    public static <T> List<T> copyList(List<?> sources, Class<T> target) {
        if (sources.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        ArrayList<T> list = new ArrayList<>(sources.size());
        ObjenesisStd objenesisStd = objenesisStdThreadLocal.get();
        for (Object source : sources) {
            if (source == null) {
                return new ArrayList<>();
            }
            T newInstance = objenesisStd.newInstance(target);
            BeanCopier beanCopier = getCacheBeanCopier(source.getClass(), target);
            beanCopier.copy(source, newInstance, null);
            list.add(newInstance);
        }
        return list;
    }

    /**
     * Map转成Bean
     * @param source
     * @param target
     * @param <T>
     * @return
     */
    public static <T> T mapToBean(Map<?, ?> source, Class<T> target) {
        T bean = objenesisStdThreadLocal.get().newInstance(target);
        BeanMap beanMap = BeanMap.create(bean);
        beanMap.putAll(source);
        return bean;
    }

    /**
     * Bean转成Map
     * @param source
     * @param <T>
     * @return
     */
    public static <T> Map<?, ?> beanToMap(T source) {
        return BeanMap.create(source);
    }

    private static <S, T> BeanCopier getCacheBeanCopier(Class<S> source, Class<T> target) {
        ConcurrentHashMap<Class<?>, BeanCopier> copierConcurrentHashMap = cache.computeIfAbsent(source, aClass -> new ConcurrentHashMap<>(16));
        return copierConcurrentHashMap.computeIfAbsent(target, aClass -> BeanCopier.create(source, target, false));
    }
}