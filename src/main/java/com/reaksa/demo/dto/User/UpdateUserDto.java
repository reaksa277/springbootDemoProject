package com.reaksa.demo.dto.User;

import com.reaksa.demo.common.annotations.ValidEnum;
import com.reaksa.demo.common.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateUserDto {
    @NotNull(message = "name is required")
    @Size(min = 4, max = 50, message = "username must be between 4 and 50 characters")
    private String name;

    @NotNull(message = "age is required")
    @Min(value = 18, message = "age must be at least 18")
    private Integer age;

    @NotNull(message = "address is required")
    @Size(min = 5, max = 50, message = "address must be between 5 and 50 characters")
    private String address;

    @NotNull(message = "role is required")
    @ValidEnum(enumClass = Role.class, message = "Role must be in [USER, ADMIN]")
    private String role;
}
