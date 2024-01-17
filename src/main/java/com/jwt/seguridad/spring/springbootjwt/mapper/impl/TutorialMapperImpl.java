package com.jwt.seguridad.spring.springbootjwt.mapper.impl;

import com.jwt.seguridad.spring.springbootjwt.dto.TutorialDto;
import com.jwt.seguridad.spring.springbootjwt.entity.TutorialEntity;
import com.jwt.seguridad.spring.springbootjwt.mapper.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TutorialMapperImpl  implements Mapper<TutorialEntity, TutorialDto> {

    private final ModelMapper modelMapper;

    public TutorialMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    /**
     * @param tutorialEntity
     * @return
     */
    @Override
    public TutorialDto mapTo(TutorialEntity tutorialEntity) {
        return modelMapper.map(tutorialEntity, TutorialDto.class);
    }

    /**
     * @param tutorialDto
     * @return
     */
    @Override
    public TutorialEntity mapFrom(TutorialDto tutorialDto) {
        return modelMapper.map(tutorialDto, TutorialEntity.class);
    }
}
