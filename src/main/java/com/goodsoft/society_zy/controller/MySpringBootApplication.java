package com.goodsoft.society_zy.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 定时器
 * Created by 龙宏 on 2017/11/23.
 */
@SpringBootApplication
//@EnableScheduling
public class MySpringBootApplication {
    private static Logger logger = LoggerFactory.getLogger(MySpringBootApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(MySpringBootApplication.class, args);
        logger.info("My Spring Boot Application Started");
    }
}
