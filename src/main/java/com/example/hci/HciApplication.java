package com.example.hci;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.example.hci.dao")
public class HciApplication {

    public static void main(String[] args) {
        SpringApplication.run(HciApplication.class, args);
    }

}
