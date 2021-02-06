package com.yipage.leanmarketing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class LeanMarketingApplication {

    public static void main(String[] args) {
        SpringApplication.run(LeanMarketingApplication.class, args);
    }


}
