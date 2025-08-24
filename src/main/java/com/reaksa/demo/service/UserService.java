package com.reaksa.demo.service;

import com.reaksa.demo.dto.User.ChangePasswordUserDto;
import com.reaksa.demo.dto.User.UpdateUserDto;
import com.reaksa.demo.dto.User.UserResponseDto;
import com.reaksa.demo.entity.User;
import com.reaksa.demo.exception.model.DuplicateResourceException;
import com.reaksa.demo.exception.model.ResourceNotFoundException;
import com.reaksa.demo.mapper.UserMapper;
import com.reaksa.demo.model.BaseResponseModel;
import com.reaksa.demo.model.BaseResponseWithDataModel;
import com.reaksa.demo.dto.User.UserDto;
import com.reaksa.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper mapper;

    public ResponseEntity<BaseResponseWithDataModel> listUser() {
        List<User> userData = userRepository.findAll();

        List<UserResponseDto> dtos = mapper.toDtoList(userData);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseWithDataModel("success", "successfully retrieved  users", dtos));
    }

    public ResponseEntity<BaseResponseWithDataModel> getUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        UserResponseDto dto = mapper.toDto(user);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseWithDataModel("success", "successfully retrieve user", dto));
    }

    public ResponseEntity<BaseResponseModel> addUser( UserDto payload) {

        // validate if username is existed
        if(userRepository.existsByName(payload.getName())) {
            throw new DuplicateResourceException("username already exists");
        }
        if(userRepository.existsByEmail(payload.getEmail())) {
            throw new DuplicateResourceException("email already exists");
        }

        User user = mapper.toEntity(payload);
        userRepository.save(user);


        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new BaseResponseModel("success", "Successfully created user"));
    }

    public ResponseEntity<BaseResponseModel> updateUser(Long userId, UpdateUserDto payload) {

        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        mapper.updateEntityFromDto(existingUser, payload);

        userRepository.save(existingUser);

        return ResponseEntity
                .status(HttpStatus.OK).
                body(new BaseResponseModel("success", "Successfully updated user"));
    }

    public ResponseEntity<BaseResponseModel> deleteUser(Long userId) {

        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }

        userRepository.deleteById(userId);

        // 204 No Content
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(new BaseResponseModel("success", "Successfully deleted user"));
    }

    public ResponseEntity<BaseResponseModel> changePassword(ChangePasswordUserDto payload, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        if (!Objects.equals(user.getPassword(), payload.getOldPassword())) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(new BaseResponseModel("fail", "old password doesn't match, please try again"));
        }

        if (!Objects.equals(payload.getNewPassword(), payload.getConfirmNewPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new BaseResponseModel("fail", "new password and confirm password doesn't match, please try again"));
        }

        mapper.updateEntityChangePassword(user, payload.getNewPassword());
        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseModel("success", "Successfully changed password"));
    }
}
