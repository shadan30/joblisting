package com.telusko.joblisting.security.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class CSRFConfig {
    // CSRF - Cross Site Request Forgery
    // securityFilterChain is used to configure CSRF for POST,PUT,DELETE requests which were not getting hit before
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf
                .csrfTokenRepository(csrfTokenRepository())
                .ignoringRequestMatchers(new AntPathRequestMatcher("/vi/posts/**", HttpMethod.GET.name()))
                .requireCsrfProtectionMatcher(new AntPathRequestMatcher("/vi/posts/**", HttpMethod.POST.name()))
                .requireCsrfProtectionMatcher(new AntPathRequestMatcher("/vi/posts/**", HttpMethod.PUT.name()))
                .requireCsrfProtectionMatcher(new AntPathRequestMatcher("/vi/posts/**", HttpMethod.DELETE.name()))
        );
        return http.build();
    }

    // We are setting CSRF token in request header in X-CSRF-TOKEN , which is setting csrf token for requests in securityFilterChain
    //  Using this csrf token , all requests can be accessible using authentication
    private CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName("X-CSRF-TOKEN");
        return repository;
    }
}
