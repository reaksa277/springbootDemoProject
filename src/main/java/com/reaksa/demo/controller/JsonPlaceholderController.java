package com.reaksa.demo.controller;

import com.reaksa.demo.dto.base.Response;
import com.reaksa.demo.dto.external.JsonPlaceholderCommentDto;
import com.reaksa.demo.dto.external.JsonPlaceholderPostDto;
import com.reaksa.demo.service.JsonPlaceholderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/json-placeholder")
public class JsonPlaceholderController {
    @Autowired
    private JsonPlaceholderService jsonPlaceholderService;

    @GetMapping("/posts")
    public ResponseEntity<Response> getPosts() {
        List<JsonPlaceholderPostDto> posts = jsonPlaceholderService.getPosts();

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.success("200", "success", "success", posts));
    }

    @GetMapping("/comments")
    public ResponseEntity<Response> getComments() {
        List<JsonPlaceholderCommentDto> comments = jsonPlaceholderService.getComments();

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.success("200", "success", "success", comments));
    }
}
