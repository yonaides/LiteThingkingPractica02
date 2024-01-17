package com.jwt.seguridad.spring.springbootjwt.mapper.impl;

import com.jwt.seguridad.spring.springbootjwt.dto.TagDto;
import com.jwt.seguridad.spring.springbootjwt.entity.TagEntity;
import com.jwt.seguridad.spring.springbootjwt.mapper.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TagMapperImpl implements Mapper<TagEntity, TagDto> {

    private final ModelMapper modelMapper;

    public TagMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    /**
     * @param tagEntity
     * @return
     */
    @Override
    public TagDto mapTo(TagEntity tagEntity) {
        return modelMapper.map(tagEntity, TagDto.class);
    }

    /**
     * @param tagDto
     * @return
     */
    @Override
    public TagEntity mapFrom(TagDto tagDto) {
        return modelMapper.map(tagDto, TagEntity.class);
    }
}
