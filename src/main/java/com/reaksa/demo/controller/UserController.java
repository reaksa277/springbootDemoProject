package com.reaksa.demo.controller;

import com.reaksa.demo.dto.User.ChangePasswordUserDto;
import com.reaksa.demo.dto.User.UpdateUserDto;
import com.reaksa.demo.dto.User.UserResponseDto;
import com.reaksa.demo.dto.base.PaginatedResponse;
import com.reaksa.demo.dto.base.Response;
import com.reaksa.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/paginated")
    public ResponseEntity<Response> listUserWithPagination(
            @PageableDefault(size = 10, page = 0, sort = "id", direction = Sort.Direction.DESC)Pageable pageable
            ) {
        PaginatedResponse<UserResponseDto> users = userService.listUserWithPagination(pageable);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.success("200", "success", "successfully retrieved users with pagination.",  users));
    }

    // used to retrieve user list
    @GetMapping
    public ResponseEntity<Response> listUsers() {
        List<UserResponseDto> users = userService.listUser();

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.success("200", "success", "successfully retrieved users", users));
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<Response> getUser(@PathVariable Long user_id) {
        UserResponseDto user = userService.getUser(user_id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.success("200", "success", "successfully retrieved user", user));
    }


    @PutMapping("/{userId}")
    public ResponseEntity<Response> updateUser(@PathVariable("userId") Long userId ,@Valid @RequestBody UpdateUserDto payload) {
        userService.updateUser(userId, payload);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.success("success", "successfully updated user!"));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Response> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.success("success", "successfully deleted user!"));
    }

    // change password
    @PatchMapping("/{user_id}/change-password")
    public ResponseEntity<Response> changePassword(@PathVariable("user_id") Long userId, @RequestBody ChangePasswordUserDto payload) {
        userService.changePassword(payload, userId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.success("success", "successfully changed password!"));
    }

}
