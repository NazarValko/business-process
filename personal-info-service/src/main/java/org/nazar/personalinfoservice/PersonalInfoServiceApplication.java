package org.nazar.personalinfoservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
@ComponentScan(basePackages = {"org.nazar.personalinfoservice", "org.nazar.common.config"})
public class PersonalInfoServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonalInfoServiceApplication.class, args);
    }

}
