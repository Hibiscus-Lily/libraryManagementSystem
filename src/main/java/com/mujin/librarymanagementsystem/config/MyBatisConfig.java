package com.mujin.librarymanagementsystem.config;

import com.github.pagehelper.PageInterceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.util.Properties;

public class MyBatisConfig {
    /**
     * 定义MyBatis的核心连接工厂bean，
     * 等同于<bean class="org.mybatis.spring.SqlSessionFactoryBean">
     * 参数使用自动装配的形式加载dataSource，
     * 为set注入提供数据源，dataSource来源于JdbcConfig中的配置
     */
    @Bean
    public SqlSessionFactoryBean getSqlSessionFactoryBean(
            @Autowired DataSource dataSource) {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        //等同于<property name="dataSource" ref="dataSource"/>
        sqlSessionFactoryBean.setDataSource(dataSource);


        //pageHelper分页配置（只参考这部分配置就可以了）
        PageInterceptor pageInterceptor = new PageInterceptor();
        Properties properties = new Properties();
        //下面这行代码可以不配置，因为在源码中会默认给properties进行如下配置
        /* properties.put("dialect", "com.github.pagehelper.PageHelper");*/
        pageInterceptor.setProperties(properties);
        sqlSessionFactoryBean.setPlugins(pageInterceptor);
        return sqlSessionFactoryBean;


    }

    /**
     * 定义MyBatis的映射扫描，
     * 等同于<bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
     */
    @Bean
    public MapperScannerConfigurer getMapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        //等同于<property name="basePackage" value="com.itheima.dao"/>
        mapperScannerConfigurer.setBasePackage("com.mujin.librarymanagementsystem.dao");
        return mapperScannerConfigurer;
    }
}