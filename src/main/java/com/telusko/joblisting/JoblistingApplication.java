package com.telusko.joblisting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class JoblistingApplication {

    public static void main(String[] args) {
        SpringApplication.run(JoblistingApplication.class, args);
    }

}
