package com.reaksa.demo.service;

import com.reaksa.demo.dto.User.UserResponseDto;
import com.reaksa.demo.entity.User;
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
        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new BaseResponseWithDataModel("fail", "user not found with id " + userId, null ));
        }

        UserResponseDto dto = mapper.toDto(user.get());

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseWithDataModel("success", "successfully retrieve user", dto));
    }

    public ResponseEntity<BaseResponseModel> addUser( UserDto payload) {

        // validate if username is existed
        if(userRepository.existsByName(payload.getName())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new BaseResponseModel("fail", "username already exists"));
        }
        if(userRepository.existsByEmail(payload.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new BaseResponseModel("fail", "email already exists"));
        }

        User user = mapper.toEntity(payload);
        userRepository.save(user);


        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new BaseResponseModel("success", "Successfully created user"));
    }

    public ResponseEntity<BaseResponseModel> updateUser(Long userId, UserDto payload) {
        Optional<User> existing = userRepository.findById(userId);

        // if user not found, then response 404
        if (existing.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new BaseResponseModel("error", "User not found id " + userId));
        }

        User updatedUser = existing.get();
        mapper.updateEntityFromDto(updatedUser, payload);

        userRepository.save(updatedUser);


        return ResponseEntity
                .status(HttpStatus.OK).
                body(new BaseResponseModel("success", "Successfully updated user"));
    }

    public ResponseEntity<BaseResponseModel> deleteUser(Long userId) {

        if (!userRepository.existsById(userId)) {
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
