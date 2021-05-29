package com.sardine.common.util;

import org.springframework.cglib.beans.BeanCopier;
import org.springframework.cglib.beans.BeanMap;

import java.util.ArrayList;
import java.util.HashMap;
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
     * convert bean to map
     *
     * @param bean source object
     * @return map
     */
    public static <T> Map<String, Object> beanToMap(T bean) {
        Map<String, Object> map = new HashMap<>();
        if (bean != null) {
            BeanMap beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                map.put(String.valueOf(key), beanMap.get(key));
            }
        }
        return map;
    }

    /**
     * convert map to bean
     *
     * @param map      source map you want converted
     * @param supplier target object supplier (eg: User::new)
     * @return target object
     */
    public static <T> T mapToBean(Map<String, Object> map, Supplier<T> supplier) {
        T bean = supplier.get();
        BeanMap beanMap = BeanMap.create(bean);
        beanMap.putAll(map);
        return bean;
    }

    /**
     * Convert List<T> to List<Map<String, Object>>
     *
     * @param sources source object list
     * @return List<Map < String, Object>>
     */
    public static <T> List<Map<String, Object>> beansToMaps(List<T> sources) {
        List<Map<String, Object>> list = new ArrayList<>();
        if (sources != null && sources.size() > 0) {
            Map<String, Object> map;
            for (T source : sources) {
                map = beanToMap(source);
                list.add(map);
            }
        }
        return list;
    }

    /**
     * Convert List<Map<String,Object>> to List<T>
     *
     * @param maps     source maps
     * @param supplier target object supplier (eg: User::new)
     * @return target object list
     */
    public static <T> List<T> mapsToBeans(List<Map<String, Object>> maps, Supplier<T> supplier) {
        List<T> list = new ArrayList<>();
        if (maps != null && maps.size() > 0) {
            Map<String, Object> map;
            for (Map<String, Object> stringObjectMap : maps) {
                map = stringObjectMap;
                list.add(mapToBean(map, supplier));
            }
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
