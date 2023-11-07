package com.ohgiraffers.datajpa.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = {"com.ohgiraffers.datajpa"})
@EnableJpaRepositories(basePackages = "com.ohgiraffers.datajpa")
public class JPAConfiguration {

}
