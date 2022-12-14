package com;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@SpringBootApplication
@EnableAsync
@EnableScheduling
public class SmiwebappApplication {

	/*@RequestMapping("/")
	@ResponseBody
	public String hello() {
		return "Hello Seal Methods";
	}*/
	
	public static void main(String[] args) {
		SpringApplication.run(SmiwebappApplication.class, args);
	}
    

	

}
