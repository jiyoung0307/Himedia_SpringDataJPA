package com.ohgiraffers.jpa.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.ohgiraffers.jpa"})
public class Chap01BootJpaLectureSourceApplication {

	public static void main(String[] args) {
		SpringApplication.run(Chap01BootJpaLectureSourceApplication.class, args);
	}

}
