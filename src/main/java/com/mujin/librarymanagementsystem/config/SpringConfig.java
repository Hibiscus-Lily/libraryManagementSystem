package com.mujin.librarymanagementsystem.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan({"com.mujin.librarymanagementsystem.service","com.mujin.librarymanagementsystem.pojo","com.mujin.librarymanagementsystem.dao","com.mujin.librarymanagementsystem.common"})

@Import({MyBatisConfig.class, JdbcConfig.class})
public class SpringConfig {
}
