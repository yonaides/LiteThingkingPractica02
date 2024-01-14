package com.jwt.seguridad.spring.springbootjwt.exception;

public class TutorialNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    public TutorialNotFoundException(String message){
        super(message);
    }
}

