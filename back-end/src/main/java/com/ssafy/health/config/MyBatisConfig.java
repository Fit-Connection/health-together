package com.ssafy.health.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.ssafy.health.dao")
public class MyBatisConfig {


}
