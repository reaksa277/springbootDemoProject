package com.reaksa.demo.controller;

import com.reaksa.demo.dto.base.Response;
import com.reaksa.demo.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/mok")
public class TestController {
    @Autowired
    private TestService testService;

    @GetMapping
    public ResponseEntity<Object> testSyncApi() {
        Object response = testService.testSyncApi();

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.success("200", "success", "success", response));
    }
}
