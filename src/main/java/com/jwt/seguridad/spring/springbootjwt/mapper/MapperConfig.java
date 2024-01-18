package com.jwt.seguridad.spring.springbootjwt.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper modelMapper(){
        ModelMapper moderMapper = new ModelMapper();
        moderMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        return new ModelMapper();
    }
}
