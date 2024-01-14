package com.jwt.seguridad.spring.springbootjwt.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class ConvertObjectToJson {

    /**
     *
     * @param object
     * @return
     * @throws JsonProcessingException
     */

    public static String convert(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
}
