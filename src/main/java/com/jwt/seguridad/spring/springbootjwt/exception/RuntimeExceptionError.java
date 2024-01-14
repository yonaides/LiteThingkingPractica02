package com.jwt.seguridad.spring.springbootjwt.exception;

public class RuntimeExceptionError extends RuntimeException  {

    private static final long serialVersionUID = 1L;

    public RuntimeExceptionError(String msg) {
        super(msg);
    }

}
