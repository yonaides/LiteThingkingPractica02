package com.jwt.seguridad.spring.springbootjwt.controller;


import com.jwt.seguridad.spring.springbootjwt.resquest.LoginUserRequest;
import com.jwt.seguridad.spring.springbootjwt.resquest.RegisterUserRequest;
import com.jwt.seguridad.spring.springbootjwt.entity.User;
import com.jwt.seguridad.spring.springbootjwt.services.AuthenticationService;
import com.jwt.seguridad.spring.springbootjwt.services.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody RegisterUserRequest registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);

        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginUserRequest.LoginResponse> authenticate(@RequestBody LoginUserRequest loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginUserRequest.LoginResponse loginResponse =
                LoginUserRequest.LoginResponse.builder()
                        .token(jwtToken)
                        .expiresIn(jwtService.getExpirationTime())
                        .build();
        return ResponseEntity.ok(loginResponse);
    }
}
