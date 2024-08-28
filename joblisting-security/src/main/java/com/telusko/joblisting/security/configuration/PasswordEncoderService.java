package com.telusko.joblisting.security.configuration;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
// Use this service in helper classes of joblisting core to encode the passwords before saving them to database
// BCryptPasswordEncoder is a hashing type of encoder
public class PasswordEncoderService {

    //Takes bean from SecurityConfig , where we have customized bean of BCryptPasswordEncoder
    @Autowired
    private BCryptPasswordEncoder encoder;

    //To encode Password
    public String encodePassword(String password){
        return encoder.encode(password);
    }

    public boolean matchesPassword(String password , String encodedPassWord){
        return encoder.matches(password,encodedPassWord);
    }

}
