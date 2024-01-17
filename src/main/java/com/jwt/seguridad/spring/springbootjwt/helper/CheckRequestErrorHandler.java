package com.jwt.seguridad.spring.springbootjwt.helper;

import com.jwt.seguridad.spring.springbootjwt.response.CustomResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.stream.Collectors;

public final class CheckRequestErrorHandler {

    /**
     *
     * @param bidingResult
     * @param entity
     * @return
     */
    public static ResponseEntity<Object> responseErrorsMessage(BindingResult bidingResult,
                                                               Object entity){
        if(bidingResult.hasErrors()){
            return CustomResponseHandler.
                    generateResponse(bidingResult
                                    .getAllErrors()
                                    .stream()
                                    .map(ObjectError::getDefaultMessage)
                                    .collect(Collectors.joining()),
                            HttpStatus.BAD_REQUEST,
                            entity);
        }
        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }

}
