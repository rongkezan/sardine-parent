package com.demo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.io.File;

/**
 * Mybatis plus 代码生成器
 *
 * @author keith
 */
public class MybatisPlusGenerator {

    public static void main(String[] args) {
        File file = new File("mybatis-plus-demo");
        String path = file.getAbsolutePath();
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(path + "/src/test/java");
        gc.setSwagger2(true);
        gc.setOpen(false);
        gc.setFileOverride(true);
        gc.setBaseResultMap(true);
        gc.setBaseColumnList(true);
        gc.setAuthor("yesitao");
        gc.setIdType(IdType.ASSIGN_ID);
        gc.setEntityName("%sDo");
        gc.setServiceName("%sManager");
        gc.setServiceImplName("%sManagerImpl");
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUrl("jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai");
        dsc.setUsername("root");
        dsc.setPassword("rkz123456");
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.demo");
        pc.setEntity("entity.model");
        pc.setMapper("mapper");
        pc.setService("manager");
        pc.setServiceImpl("manager.impl");
        pc.setXml("sqlmap");
        mpg.setPackageInfo(pc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        strategy.setInclude("t_wallet");
        strategy.setTablePrefix("t");
        mpg.setStrategy(strategy);
        mpg.execute();
    }
}
