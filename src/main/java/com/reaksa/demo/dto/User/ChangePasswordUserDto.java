package com.reaksa.demo.dto.User;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ChangePasswordUserDto {
    @NotNull(message = "old password is required")
    @Size(min = 8, max = 12, message = "password must be between 8 and 12 characters")
    private String oldPassword;

    @NotNull(message = "new password is required")
    @Size(min = 8, max = 12, message = "password must be between 8 and 12 characters")
    private String newPassword;

    @NotNull(message = "confirm password is required")
    @Size(min = 8, max = 12, message = "password must be between 8 and 12 characters")
    private String confirmNewPassword;
}
