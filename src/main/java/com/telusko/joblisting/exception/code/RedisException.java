package com.telusko.joblisting.exception.code;

public class RedisException extends RuntimeException{
    public RedisException(String message){
        super(message);
    }
}
