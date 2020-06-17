package com.fota.vehiclefeatures;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@EnableScheduling
@SpringBootApplication
@EnableSwagger2
public class VehicleFeaturesApplication {

    public static void main(String[] args) {
        SpringApplication.run(VehicleFeaturesApplication.class, args);
    }
    public Docket swaggerConfiguration(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.ant("/fota/*"))
                .apis(RequestHandlerSelectors.basePackage("com.fota.vehiclefeatures"))
                .build()
                .apiInfo(apiDetails());
    }
    private ApiInfo apiDetails(){
        return new ApiInfo("MAN FOTA Challenge","API to query installable and incompatible features on trucks.","1.0.0","fota.com",new Contact("","",""),"","", Collections.emptyList());
    }


}
