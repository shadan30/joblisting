package com.telusko.joblisting.security.configuration;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.List;

@Configuration
@AllArgsConstructor
public class UserServiceConfig {
    @Value("${spring.security.user.name}")
    private List<String> usernames;

    @Value("${spring.security.user.password}")
    private List<String> passwords;

    @Value("${spring.security.user.roles}")
    private List<String> roles;

    /**
     * Creates a UserDetailsService bean that manages users in memory.
     *
     * @return an instance of InMemoryUserDetailsManager populated with users from the usernames, passwords, and roles lists.
     */
    //When a user submits a login request, the UsernamePasswordAuthenticationFilter intercepts the request and extracts the username and password from the request.
    //The UsernamePasswordAuthenticationFilter then calls the UserDetailsService to retrieve UserDetails object for the user
    //The UserDetails object is then passed to AuthenticationManager to authenticate the user
    @Bean
    public UserDetailsService userDetailsService() { //map users , passwords , roles here
        //InMemoryUserDetailsManager is a simple implementation of the UserDetailsService interface in Spring Security
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        for (int i = 0; i < usernames.size(); i++) {
            manager.createUser(User.withUsername(usernames.get(i))
                    .password(passwordEncoder().encode(passwords.get(i)))
                    .roles(roles.get(i))
                    .build());
        }
        return manager;
    }

    //The passwordEncoder() is used to encode the passwords before storing them in the InMemoryUserDetailsManager.
    //This is a security best practice to prevent storing passwords in plain text
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // this encoding will be applied to passwords
    }

}
