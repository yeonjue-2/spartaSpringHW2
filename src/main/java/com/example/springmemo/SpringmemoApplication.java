package com.example.springmemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SpringmemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringmemoApplication.class, args);
    }

}
