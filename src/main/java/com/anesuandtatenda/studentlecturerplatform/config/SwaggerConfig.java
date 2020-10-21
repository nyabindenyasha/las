package com.anesuandtatenda.studentlecturerplatform.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
    public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
                .select()                 .apis(RequestHandlerSelectors.basePackage("com.anesuandtatenda.studentlecturerplatform"))
                .paths(regex(".*"))
                .build()
                .apiInfo(metaData());
    }

    // Describe your apis
    private ApiInfo metaData() {
        return new ApiInfoBuilder()
                .title("Lecturer Appointment System Swagger")
                .description("This page lists all the rest apis for Lecturer Appointment System.")
                .version("1.0-SNAPSHOT")
                .build();
    }
    
  
}
