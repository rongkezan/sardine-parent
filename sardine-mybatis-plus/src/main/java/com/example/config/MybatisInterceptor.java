//package com.example;
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.ibatis.executor.Executor;
//import org.apache.ibatis.mapping.MappedStatement;
//import org.apache.ibatis.mapping.SqlCommandType;
//import org.apache.ibatis.plugin.Interceptor;
//import org.apache.ibatis.plugin.Intercepts;
//import org.apache.ibatis.plugin.Invocation;
//import org.apache.ibatis.plugin.Signature;
//import org.springframework.stereotype.Component;
//
//import java.lang.reflect.Field;
//import java.time.LocalDateTime;
//import java.util.Map;
//
///**
// * Mybatis 拦截器
// * 自动注入东八区的 gmtCreate, gmtModified
// *
// * @author keith
// */
//@Slf4j
//@Component
//@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
//public class MybatisInterceptor implements Interceptor {
//
//    private static final String CREATE_TIME = "createTime";
//
//    private static final String MODIFY_TIME = "modifyTime";
//
//    @Override
//    public Object intercept(Invocation invocation) throws Throwable {
//        Object[] args = invocation.getArgs();
//        MappedStatement mappedStatement = (MappedStatement) args[0];
//        Object param = args[1];
//
//        if (param == null) {
//            return invocation.proceed();
//        }
//
//        Object entity;
//        if (param instanceof Map) {
//            Map<String, Object> map = (Map<String, Object>) param;
//            entity = map.get("et");
//        } else {
//            entity = param;
//        }
//        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
//        if (SqlCommandType.INSERT == sqlCommandType) {
//            Field[] fields = entity.getClass().getDeclaredFields();
//            for (Field field : fields) {
//                if (StringUtils.equals(field.getName(), CREATE_TIME) || StringUtils.equals(field.getName(), MODIFY_TIME)) {
//                    field.setAccessible(true);
//                    field.set(entity, LocalDateTime.now());
//                }
//            }
//        } else if (SqlCommandType.UPDATE == sqlCommandType) {
//            Field[] fields = entity.getClass().getDeclaredFields();
//            for (Field field : fields) {
//                if (StringUtils.equals(field.getName(), MODIFY_TIME)) {
//                    field.setAccessible(true);
//                    field.set(entity, LocalDateTime.now());
//                }
//            }
//        }
//        return invocation.proceed();
//    }
//}
