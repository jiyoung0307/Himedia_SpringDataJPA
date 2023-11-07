package com.ohgiraffers.jpa.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan(basePackages = {"com.ohgiraffers.jpa"})
public class JPAConfiguration {
}
