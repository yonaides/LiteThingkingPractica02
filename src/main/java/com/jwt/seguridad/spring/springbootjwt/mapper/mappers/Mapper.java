package com.jwt.seguridad.spring.springbootjwt.mapper.mappers;

public interface Mapper <A,B>{

    B mapTo(A a);

    A mapFrom(B b);
}
