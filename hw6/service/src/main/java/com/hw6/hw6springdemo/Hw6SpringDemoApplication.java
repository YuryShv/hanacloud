package com.hw6.hw6springdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
@SpringBootApplication
@ServletComponentScan("com.sap.cloud.sdk") 
public class Hw6SpringDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(Hw6SpringDemoApplication.class, args);
	}

}

