package com.reaksa.demo.controller;

import com.reaksa.demo.dto.User.ChangePasswordUserDto;
import com.reaksa.demo.dto.User.UpdateUserDto;
import com.reaksa.demo.model.BaseResponseModel;
import com.reaksa.demo.model.BaseResponseWithDataModel;
import com.reaksa.demo.dto.User.UserDto;
import com.reaksa.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // used to retrieve user list
    @GetMapping
    public ResponseEntity<BaseResponseWithDataModel> listUsers() {
        return userService.listUser();
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<BaseResponseWithDataModel> getUser(@PathVariable Long user_id) {
        return userService.getUser(user_id);
    }

    // used for create/inserting record
    // request body can be called request
    @PostMapping
    public ResponseEntity<BaseResponseModel> addUser(@Valid @RequestBody UserDto payload) {
        return userService.addUser(payload);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<BaseResponseModel> updateUser(@PathVariable("userId") Long userId ,@Valid @RequestBody UpdateUserDto payload) {
        return userService.updateUser(userId, payload);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<BaseResponseModel> deleteUser(@PathVariable Long userId) {
        return userService.deleteUser(userId);
    }

    // change password
    @PatchMapping("/{user_id}/change-password")
    public ResponseEntity<BaseResponseModel> changePassword(@PathVariable("user_id") Long userId, @RequestBody ChangePasswordUserDto payload) {

        return userService.changePassword(payload, userId);
    }

}
