package com.agency;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class DigitalAgencyDashboardApplication {

    public static void main(String[] args) {
        SpringApplication.run(DigitalAgencyDashboardApplication.class, args);
    }
}
