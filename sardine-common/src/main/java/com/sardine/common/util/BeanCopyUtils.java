package com.sardine.common.util;

import org.springframework.cglib.beans.BeanCopier;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * Bean复制工具类
 * 如果是使用 @Builder 或 @Accessors(chain = true) 则无法使用该工具类进行复制
 *
 * @author keith
 */
public class BeanCopyUtils {

    /**
     * 缓存BeanCopier 对象 提升性能
     */
    private static final ConcurrentHashMap<String, BeanCopier> BEAN_COPIER_MAP = new ConcurrentHashMap<>();

    /**
     * Bean属性复制工具方法。
     * @param source    目标对象
     * @param supplier  目标类::new(eg: UserVO::new)
     */
    public static <T> T copyBean(Object source, Supplier<T> supplier) {
        T t = supplier.get();
        getBeanCopier(source.getClass(), t.getClass()).copy(source, t, null);
        return t;
    }

    /**
     * Bean属性复制工具方法。
     * @param sources   原始集合
     * @param supplier  目标类::new(eg: UserVO::new)
     */
    public static <S, T> List<T> copyList(List<S> sources, Supplier<T> supplier) {
        List<T> list = new ArrayList<>(sources.size());
        org.springframework.cglib.beans.BeanCopier beanCopier = null;
        for (S source : sources) {
            T t = supplier.get();
            if (beanCopier == null) {
                beanCopier = getBeanCopier(source.getClass(), t.getClass());
            }
            beanCopier.copy(source, t, null);
            list.add(t);
        }
        return list;
    }

    /**
     * 获取BeanCopier对象 如果缓存中有从缓存中获取 如果没有则新创建对象并加入缓存
     */
    private static BeanCopier getBeanCopier(Class<?> sourceClass, Class<?> targetClass) {
        String key = getKey(sourceClass.getName(), targetClass.getName());
        BeanCopier beanCopier = null;
        if (BEAN_COPIER_MAP.containsKey(key)){
            beanCopier = BEAN_COPIER_MAP.get(key);
        } else {
            beanCopier = BeanCopier.create(sourceClass, targetClass, false);
            BEAN_COPIER_MAP.put(key, beanCopier);
        }
        return beanCopier;
    }

    private static String getKey(String sourceClassName, String targetClassName) {
        return sourceClassName + targetClassName;
    }
}
