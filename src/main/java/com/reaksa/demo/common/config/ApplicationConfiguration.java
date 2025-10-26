package com.reaksa.demo.common.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
@ConfigurationProperties(prefix = "config")
@Getter
@Setter
public class ApplicationConfiguration {
    private Security security;
    private Pagination pagination;
    private JsonPlaceholder jsonPlaceholder;

    @Getter
    @Setter
    public static class Security {
        private String secret;
        private long expiration;
        private long refreshTokenExpiration;
    }

    @Getter
    @Setter
    public static class Pagination {
        private String baseUrl;
        private HashMap<String,String> uri;

        public String getUrlByResourse(String resourse) {
            return baseUrl.concat(uri.getOrDefault(resourse,""));
        }
    }

    @Getter
    @Setter
    public static class JsonPlaceholder {
        private String baseUrl;
        private HashMap<String,String> uri;

        public String getPostUri() {
            return uri.get("post");
        }

        public String getCommentsUri() {
            return uri.get("comments");
        }
    }
}
