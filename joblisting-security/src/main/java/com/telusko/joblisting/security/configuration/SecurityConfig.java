package com.telusko.joblisting.security.configuration;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.List;

@Configuration
@AllArgsConstructor
@EnableWebSecurity // If we don't use this , spring will apply default security configurations
public class SecurityConfig {
    @Value("${spring.security.user.name}")
    private List<String> usernames;

    @Value("${spring.security.user.password}")
    private List<String> passwords;

    @Value("${spring.security.user.roles}")
    private List<String> roles;

    @Bean
    public UserDetailsService userDetailsService() { //map users , passwords , roles here
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        for (int i = 0; i < usernames.size(); i++) {
            manager.createUser(User.withUsername(usernames.get(i))
                    .password(passwordEncoder().encode(passwords.get(i)))
                    .roles(roles.get(i))
                    .build());
        }
        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // this encoding will be applied to passwords
    }

}
