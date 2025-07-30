package com.reaksa.demo.dto.User;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String name;
    private Integer age;
    private String address;
    private String password;
    private String email;
    private String role;

}

