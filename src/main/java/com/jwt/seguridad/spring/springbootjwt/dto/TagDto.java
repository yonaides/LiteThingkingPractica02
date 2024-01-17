package com.jwt.seguridad.spring.springbootjwt.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jwt.seguridad.spring.springbootjwt.entity.TutorialEntity;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TagDto {

    private long id;
    private String name;
    @JsonIgnore
    private Set<TutorialEntity> tutorials = new HashSet<>();
}
