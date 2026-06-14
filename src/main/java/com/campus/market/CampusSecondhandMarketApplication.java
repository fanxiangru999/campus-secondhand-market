package com.campus.market;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 校园二手物品交易平台主应用类
 */
@SpringBootApplication
@MapperScan("com.campus.market.mapper")
public class CampusSecondhandMarketApplication {

    public static void main(String[] args) {
        SpringApplication.run(CampusSecondhandMarketApplication.class, args);
        System.out.println("========================================");
        System.out.println("校园二手物品交易平台启动成功！");
        System.out.println("访问地址: http://localhost:8080");
        System.out.println("========================================");
    }
}
