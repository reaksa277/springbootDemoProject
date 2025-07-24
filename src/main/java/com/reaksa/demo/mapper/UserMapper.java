package com.reaksa.demo.mapper;

import com.reaksa.demo.dto.UserDto;
import com.reaksa.demo.dto.UserResponseDto;
import com.reaksa.demo.entity.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public User toEntity(UserDto dto) {
        User entity = new User();

        entity.setName(dto.getName());
        entity.setPassword(dto.getPassword());
        entity.setEmail(dto.getEmail());
        entity.setAddress(dto.getAddress());
        entity.setAge(dto.getAge());
        entity.setRole(dto.getRole());
        entity.setCreatedAt(LocalDateTime.now());

        return entity;
    }

    public void updateEntityFromDto(User entity, UserDto dto) {
        if (entity == null || dto == null) {
            return;
        }

        entity.setName(dto.getName());
        entity.setAge(dto.getAge());
        entity.setEmail(dto.getEmail());
        entity.setAddress(dto.getAddress());
        entity.setPassword(dto.getPassword());
        entity.setRole(dto.getRole());
    }

    public UserResponseDto toDto(User entity) {
        UserResponseDto dto = new UserResponseDto();

        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setAge(entity.getAge());
        dto.setAddress(entity.getAddress());
        dto.setEmail(entity.getEmail());
        dto.setRole(entity.getRole());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());

        return dto;
    }

    public List<UserResponseDto> toDtoList(List<User> entities) {
        if (entities == null || entities.isEmpty()) {
            return new ArrayList<>();
        }

        return entities.stream()
                .map(user -> this.toDto(user))
                .collect(Collectors.toList());
    }
}
