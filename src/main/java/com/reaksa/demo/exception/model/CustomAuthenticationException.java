package com.reaksa.demo.exception.model;

public class CustomAuthenticationException extends RuntimeException{
    public CustomAuthenticationException(String message){
        super(message);
    }
}
