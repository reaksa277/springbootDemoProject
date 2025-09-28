package com.reaksa.demo.service;

import com.reaksa.demo.dto.User.ChangePasswordUserDto;
import com.reaksa.demo.dto.User.UpdateUserDto;
import com.reaksa.demo.dto.User.UserResponseDto;
import com.reaksa.demo.entity.User;
import com.reaksa.demo.exception.model.ResourceNotFoundException;
import com.reaksa.demo.exception.model.UnprocessableEntityException;
import com.reaksa.demo.mapper.UserMapper;
import com.reaksa.demo.repository.UserRepository;
import com.reaksa.demo.service.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper mapper;

    @Autowired
    private JwtUtil jwtUtil;

    public List<UserResponseDto> listUser() {
        List<User> users = userRepository.findAll();

        return mapper.toDtoList(users);
    }

    public UserResponseDto getUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        return mapper.toDto(user);
    }


    public void updateUser(Long userId, UpdateUserDto payload) {

        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        mapper.updateEntityFromDto(existingUser, payload);

        userRepository.save(existingUser);
    }

    public void deleteUser(Long userId) {

        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }

        userRepository.deleteById(userId);
    }

    public void changePassword(ChangePasswordUserDto payload, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        // old password is incorrect
        if (!Objects.equals(user.getPassword(), payload.getOldPassword())) {
            throw new UnprocessableEntityException("old password not match");
        }

        // new password and confirm password not match
        if (!Objects.equals(payload.getNewPassword(), payload.getConfirmNewPassword())) {
            throw new UnprocessableEntityException("new password and confirm password must be the same");
        }

        mapper.updateEntityChangePassword(user, payload.getNewPassword());
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByName(username)
                .orElseThrow(() -> {
                    throw new UsernameNotFoundException("user not found with username: " + username);
                });
    }
}
