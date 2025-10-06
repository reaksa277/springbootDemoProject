package com.reaksa.demo.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RefreshTokenDto {
    @JsonProperty("refresh_token")
    private String refreshToken;
}
