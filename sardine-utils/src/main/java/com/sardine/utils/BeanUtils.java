package com.sardine.utils;

import org.springframework.cglib.beans.BeanCopier;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

/**
 * Bean properties copy based on Cglib, Performance is much better than
 * {@link org.springframework.beans.BeanUtils}.
 * Chain invoke or builder object is forbidden cause it will copy failed.
 *
 * @author keith
 */
@SuppressWarnings("unused")
public class BeanUtils {

    private static final Map<String, BeanCopier> BEAN_COPIER_MAP = new ConcurrentHashMap<>();

    /**
     * Copy bean object
     *
     * @param source   object you want copy
     * @param target   target object
     */
    public static <T> T copyProperties(Object source, T target) {
        getBeanCopier(source.getClass(), target.getClass()).copy(source, target, null);
        return target;
    }

    /**
     * Copy bean object
     *
     * @param source   object you want copy
     * @param supplier target object supplier (eg: User::new)
     */
    public static <T> T copyProperties(Object source, Supplier<T> supplier) {
        T t = supplier.get();
        getBeanCopier(source.getClass(), t.getClass()).copy(source, t, null);
        return t;
    }

    /**
     * Copy bean object
     *
     * @param source   object you want copy
     * @param supplier target object supplier (eg: User::new)
     * @param consumer additional action
     */
    public static <S, T> T copyProperties(S source, Supplier<T> supplier, BiConsumer<S, T> consumer) {
        T t = supplier.get();
        getBeanCopier(source.getClass(), t.getClass()).copy(source, t, null);
        if (consumer != null) {
            consumer.accept(source, t);
        }
        return t;
    }

    /**
     * Copy bean object list
     *
     * @param sources  object list you want copy
     * @param supplier target object supplier (eg: User::new)
     */
    public static <S, T> List<T> copyListProperties(List<S> sources, Supplier<T> supplier) {
        return copyListProperties(sources, supplier, null);
    }

    /**
     * Copy bean object list with a additional action.
     *
     * @param sources  object list you want copy
     * @param supplier target object supplier (eg: User::new)
     * @param consumer additional action
     */
    public static <S, T> List<T> copyListProperties(List<S> sources, Supplier<T> supplier, BiConsumer<S, T> consumer) {
        List<T> list = new ArrayList<>(sources.size());
        BeanCopier beanCopier = null;
        for (S source : sources) {
            T t = supplier.get();
            if (beanCopier == null) {
                beanCopier = getBeanCopier(source.getClass(), t.getClass());
            }
            beanCopier.copy(source, t, null);
            if (consumer != null) {
                consumer.accept(source, t);
            }
            list.add(t);
        }
        return list;
    }

    /**
     * Get BeanCopier if cache exists, or else create it.
     */
    private static BeanCopier getBeanCopier(Class<?> sourceClass, Class<?> targetClass) {
        String key = sourceClass.getName() + targetClass.getName();
        BeanCopier beanCopier;
        if (BEAN_COPIER_MAP.containsKey(key)) {
            beanCopier = BEAN_COPIER_MAP.get(key);
        } else {
            beanCopier = BeanCopier.create(sourceClass, targetClass, false);
            BEAN_COPIER_MAP.put(key, beanCopier);
        }
        return beanCopier;
    }
}
