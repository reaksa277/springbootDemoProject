package com.reaksa.demo.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponseDto {
    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("refreshToken")
    private String refreshToken;
}
