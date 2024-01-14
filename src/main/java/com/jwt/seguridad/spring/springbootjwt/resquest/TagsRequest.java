package com.jwt.seguridad.spring.springbootjwt.resquest;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TagsRequest {

    @NotBlank
    @Size(min=5)
    private String name;

}
