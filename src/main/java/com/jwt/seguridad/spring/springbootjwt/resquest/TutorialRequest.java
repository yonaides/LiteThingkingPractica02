package com.jwt.seguridad.spring.springbootjwt.resquest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TutorialRequest {

    @NotBlank
    @Size(min=10)
    private String title;
    @NotBlank
    @Size(min=20)
    private String description;
    private boolean published;
}
