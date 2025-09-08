package ru.cource.inno.java_pro.limits;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LimitsApplication {
    public static void main(String[] args) {
        SpringApplication.run(LimitsApplication.class, args);
    }
}