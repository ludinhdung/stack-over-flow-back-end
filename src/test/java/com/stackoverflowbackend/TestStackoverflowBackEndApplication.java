package com.stackoverflowbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestStackoverflowBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.from(StackoverflowBackEndApplication::main).with(TestStackoverflowBackEndApplication.class).run(args);
	}

}
