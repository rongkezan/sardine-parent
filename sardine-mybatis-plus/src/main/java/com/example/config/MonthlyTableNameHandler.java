package com.example.config;

import com.baomidou.mybatisplus.extension.plugins.handler.TableNameHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
public class MonthlyTableNameHandler implements TableNameHandler {

    private static final String TABLE_NAME_DATE_PATTERN = "_yyyy_MM";

    @Override
    public String dynamicTableName(String sql, String tableName) {
        log.info("sql: {}", sql);
        log.info("tableName: {}", tableName);
        if (StringUtils.equals("tb_user", tableName)) {
            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TABLE_NAME_DATE_PATTERN);
            String tableNameSuffix = currentDate.format(formatter);
            return tableName + tableNameSuffix;
        }
        return tableName;

    }
}
