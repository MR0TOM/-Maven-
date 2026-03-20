package com.smartpark.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.mybatis.spring.annotation.MapperScan;

@SpringBootApplication(scanBasePackages = {"com.smartpark"})
@MapperScan("com.smartpark.mapper")
@EnableConfigurationProperties
public class EnergyManagementApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(EnergyManagementApiApplication.class, args);
    }
}
