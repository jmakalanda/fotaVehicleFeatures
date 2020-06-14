package com.fota.vehiclefeatures;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class VehicleFeaturesApplication {

    public static void main(String[] args) {
        SpringApplication.run(VehicleFeaturesApplication.class, args);
    }

}
