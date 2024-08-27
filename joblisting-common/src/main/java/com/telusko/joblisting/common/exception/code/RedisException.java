package com.telusko.joblisting.common.exception.code;

public class RedisException extends RuntimeException{
    public RedisException(String message){
        super(message);
    }
}
