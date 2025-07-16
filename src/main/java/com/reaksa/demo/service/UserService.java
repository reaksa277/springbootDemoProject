package com.reaksa.demo.service;

import com.reaksa.demo.entity.User;
import com.reaksa.demo.model.BaseResponseModel;
import com.reaksa.demo.model.UserModel;
import com.reaksa.demo.model.UserResponseModel;
import com.reaksa.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private List<UserModel> users = new ArrayList<>(Arrays.asList(
            new UserModel(1L, "Reaksa", 23, "Phnom Penh", "example@gmail.com", "admin")
    ));

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<UserResponseModel> getUser() {
        List<User> userData = userRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK)
                .body(new UserResponseModel("success", "successfully retrieved  users", userData));
    }

    public ResponseEntity<BaseResponseModel> addUser( UserModel payload) {
        User user = new User();
        user.setName(payload.getName());
        user.setAddress(payload.getAddress());
        user.setAge(payload.getAge());
        user.setEmail(payload.getEmail());
        user.setCreateAt(LocalDateTime.now());
        user.setRole(payload.getRole());
        userRepository.save(user);


        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new BaseResponseModel("success", "Successfully created user"));
    }

    public ResponseEntity<BaseResponseModel> updateUser(Long userId, UserModel payload) {
        Optional<User> existing = userRepository.findById(userId);

        // if user not found, then response 404
        if (existing.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new BaseResponseModel("error", "User not found id " + userId));
        }

        User updatedUser = existing.get();
        updatedUser.setName(payload.getName());
        updatedUser.setAddress(payload.getAddress());
        updatedUser.setAge(payload.getAge());
        updatedUser.setEmail(payload.getEmail());
        updatedUser.setRole(payload.getRole());

        userRepository.save(updatedUser);


        return ResponseEntity
                .status(HttpStatus.OK).
                body(new BaseResponseModel("success", "Successfully updated user"));
    }

    public ResponseEntity<BaseResponseModel> deleteUser(Long userId) {

        if (userRepository.existsById(userId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new BaseResponseModel("error", "User not found id: " + userId));
        }

        userRepository.deleteById(userId);

        // 204 No Content
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(new BaseResponseModel("success", "Successfully deleted user"));
    }
}
