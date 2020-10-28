package com.java.activiti.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableWebMvc
@EnableSwagger2
@ComponentScan("com.java.activiti.controller")
public class SwaggerConfig {

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select().apis(RequestHandlerSelectors.basePackage("com.java.activiti.controller"))// controller路径。
                .paths(PathSelectors.any())
                .build();
    }

    // 配置 API 文档标题、描述、作者等等相关信息。
    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("前后端分离API接口文档")
                .termsOfServiceUrl("")
                .description("运管控系统中使用Swagger2构建Restful API")
                .build();
    }
}
