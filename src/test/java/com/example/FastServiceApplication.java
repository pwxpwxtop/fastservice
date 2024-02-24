package com.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "com.example",
})
@MapperScan("com.example.mapper")
public class FastServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FastServiceApplication.class, args);

    }

}
