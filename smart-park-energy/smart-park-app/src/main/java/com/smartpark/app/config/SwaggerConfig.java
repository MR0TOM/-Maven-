package com.smartpark.app.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger 配置类
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("智慧园区能耗管理平台 API")
                        .version("1.0.0")
                        .description("智慧园区能耗管理平台接口文档")
                        .contact(new Contact()
                                .name("SmartPark Team")
                                .email("support@smartpark.com")));
    }
}
