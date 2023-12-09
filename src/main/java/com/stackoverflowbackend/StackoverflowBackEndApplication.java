package com.stackoverflowbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class StackoverflowBackEndApplication {

    public static void main(String[] args) {
        SpringApplication.run(StackoverflowBackEndApplication.class, args);
    }

}
