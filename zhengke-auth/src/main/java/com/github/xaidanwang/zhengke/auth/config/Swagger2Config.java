package com.github.xaidanwang.zhengke.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @ClassName Swagger2Config
 * @Version: 1.0
 * @Description Swagger配置类
 * @Author min.Zhang
 * @create: 2019-04-01 09:48
 **/
@EnableSwagger2
@Configuration
public class Swagger2Config {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.github.xaidanwang.zhengke.auth.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("郑柯脚本权限管理平台")
                .description("权限管理平台")
                .termsOfServiceUrl("")
                .contact(new Contact("王亦非", "https://github.com/xaidanwang/spring-boot/tree/feature/zhengkeAuthModule", "wangx982770631@163.com"))
                .version("1.0")
                .build();
    }
}
