package com.reaksa.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponseDto {
    @JsonProperty("user_id")
    private Long id;

    @JsonProperty("username")
    private String name;
    private Integer age;

    @JsonProperty("location")
    private String address;
    private String email;
    private String role =  "USER";

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;
}
