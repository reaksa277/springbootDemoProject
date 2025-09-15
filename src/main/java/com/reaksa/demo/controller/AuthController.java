package com.reaksa.demo.controller;

import com.reaksa.demo.dto.User.UserDto;
import com.reaksa.demo.dto.base.Response;
import com.reaksa.demo.service.security.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping
    public ResponseEntity<Response> register(@RequestBody UserDto payload) {
        String token = authService.register(payload);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Response.success("201", "success", token));
    }
}
