package org.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("org.example.mapper")
@SpringBootApplication
public class UserManageMain {
    public static void main(String[] args) {
        SpringApplication.run(UserManageMain.class,args);
    }
}