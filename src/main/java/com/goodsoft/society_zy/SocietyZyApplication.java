package com.goodsoft.society_zy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

/**
 * function 系统启动程序入口
 * Created by 严彬荣 on 2017/8/4.
 * version v1.0
 */
@ComponentScan(basePackages = "com.goodsoft.society_zy.*")
@ServletComponentScan(basePackages = "com.goodsoft.society_zy.config.*")
@SpringBootApplication
public class SocietyZyApplication implements CommandLineRunner {
    //实例化日志管理
    private final Logger logger = LoggerFactory.getLogger(SocietyZyApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SocietyZyApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        this.logger.info("=================>系统启动成功<==============");
        System.out.println("=================>系统启动成功<==============");
    }
}
