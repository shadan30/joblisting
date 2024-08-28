package com.telusko.joblisting.security.configuration;

import com.telusko.joblisting.common.model.User;
import com.telusko.joblisting.database.repository.IUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Service
@Slf4j
public class UserService implements UserDetailsService {

    @Autowired
    IUserRepository userRepository;

    // We have to implement loadUserByUsername if we are implementing UserDetailsService interface
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(userName); // fetching user details from database using username
        if(isNull(user)){
            log.error("User Not found for username : {}",userName);
            throw new UsernameNotFoundException("User Not found for username : "+userName);
        }
        return new UserDetailsImpl(user); // We will return implementation of UserDetails , since it is an interface
    }
}
