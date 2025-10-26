package com.reaksa.demo.common.wrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;

import java.time.Duration;

@Configuration
public class WebClientWrapper {

    @Autowired
    private WebClient webClient;

    public <T> T getSync(String url, Class<T> responseType) {
        return webClient
                .get()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(responseType)
                .timeout(Duration.ofMillis(5000))
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(1)))
                .block();
    }

    public <T> T postSync(String url,Object payload, Class<T> responseType) {
        return webClient
                .post()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(responseType)
                .timeout(Duration.ofMillis(5000))
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(1)))
                .block();
    }

}
