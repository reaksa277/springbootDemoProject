package com.reaksa.demo.service;

import com.reaksa.demo.common.config.ApplicationConfiguration;
import com.reaksa.demo.common.wrapper.WebClientWrapper;
import com.reaksa.demo.dto.external.JsonPlaceholderCommentDto;
import com.reaksa.demo.dto.external.JsonPlaceholderPostDto;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class JsonPlaceholderService {
    @Autowired
    private WebClientWrapper  webClientWrapper;

    @Autowired
    private ApplicationConfiguration  appConfig;

    private String BASE_URL;
    private String POSTS_URL;
    private String COMMENTS_URL;

    @PostConstruct
    private void init(){
        this.BASE_URL = appConfig.getJsonPlaceholder().getBaseUrl();
        this.POSTS_URL = appConfig.getJsonPlaceholder().getPostUri();
        this.COMMENTS_URL = appConfig.getJsonPlaceholder().getCommentsUri();
    }

    public List<JsonPlaceholderPostDto> getPosts() {
        String url = BASE_URL.concat(POSTS_URL);

        List<JsonPlaceholderPostDto> response = (List<JsonPlaceholderPostDto>)webClientWrapper.getSync(url, List.class);

        return response;
    }

    public List<JsonPlaceholderCommentDto> getComments() {
        String url = BASE_URL.concat(COMMENTS_URL);

        List<JsonPlaceholderCommentDto> response = (List<JsonPlaceholderCommentDto>)webClientWrapper.getSync(url, List.class);

        return response;
    }
}
