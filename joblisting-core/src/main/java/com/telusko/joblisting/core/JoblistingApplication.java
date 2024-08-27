package com.telusko.joblisting.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

//It tells Spring Boot to scan the specified packages and their sub-packages for Spring-managed beans.
// this will scan basic spring components like @Service , @Controller , @Component
// The scanBasePackages attribute is concerned with package names, not Maven group IDs.
@SpringBootApplication(scanBasePackages = "com.telusko")
//It tells Spring Data MongoDB to look in the specified packages for repository interfaces.
// Again, this is about package names, not Maven coordinates.
@EnableMongoRepositories(basePackages = "com.telusko")
@EnableCaching
public class JoblistingApplication {

    public static void main(String[] args) {
        SpringApplication.run(JoblistingApplication.class, args);
    }

}
