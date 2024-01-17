package com.jwt.seguridad.spring.springbootjwt.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jwt.seguridad.spring.springbootjwt.entity.TagEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import org.hibernate.validator.constraints.Length;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TutorialDto {

    private long id;
    @NotNull
    @NotBlank
    @Length(min = 5)
    private String title;
    @NotNull
    @NotBlank
    @Length(min = 10)
    private String description;
    private boolean published;
    private Set<TagDto> tags = new HashSet<>();
    private java.util.Calendar createdDate;
    private java.util.Calendar modifiedDate;

}
