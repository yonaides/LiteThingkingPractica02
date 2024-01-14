package com.jwt.seguridad.spring.springbootjwt.resquest;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class LoginUserRequest {

    private String email;
    private String password;

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class LoginResponse {

        private String token;
        private long expiresIn;

    }
}
