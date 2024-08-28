package com.telusko.joblisting.security.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity// If we don't use this , spring will apply default security configurations
//Use this annotation only in one place , as it tells the entry point for Spring Security
public class CSRFConfig {
    // CSRF - Cross Site Request Forgery
    // securityFilterChain is used to configure CSRF for POST,PUT,DELETE requests which were not getting hit before
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //disable csrf
        http.csrf(csrfCustomizer -> csrfCustomizer.disable()) // disables csrf protection for our application
        // authenticate any request -> enable authentication , but we will bypass the security layer and get 403 forbidden in Postman Client
        .authorizeHttpRequests(request -> request.anyRequest().authenticated())  //requires authentication for all incoming requests
        // will give the login page with defaults (on UI) , and we will get response in postman , but postman response is in HTML instead of JSON
        .formLogin(Customizer.withDefaults()) // enables form-based login for our application, using default settings
        // Will get proper JSON response in postman
        .httpBasic(Customizer.withDefaults()); // enables http basic authentication for our application
        return http.build();
    }
}
