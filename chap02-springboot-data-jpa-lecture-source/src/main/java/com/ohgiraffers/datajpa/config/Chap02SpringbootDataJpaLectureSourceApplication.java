package com.ohgiraffers.datajpa.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.ohgiraffers.datajpa"})
public class Chap02SpringbootDataJpaLectureSourceApplication {

	public static void main(String[] args) {
		SpringApplication.run(Chap02SpringbootDataJpaLectureSourceApplication.class, args);
	}

}
