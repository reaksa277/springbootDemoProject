package com.reaksa.demo.service.security;

import com.reaksa.demo.dto.User.UserDto;
import com.reaksa.demo.dto.auth.AuthDto;
import com.reaksa.demo.dto.auth.AuthResponseDto;
import com.reaksa.demo.entity.User;
import com.reaksa.demo.exception.model.DuplicateResourceException;
import com.reaksa.demo.mapper.UserMapper;
import com.reaksa.demo.repository.UserRepository;
import com.reaksa.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper mapper;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private RefreshTokenService  refreshTokenService;

    public AuthResponseDto register(UserDto payload) {
        // validate if username is existed
        if(userRepository.existsByName((payload.getName()))) {
            throw new DuplicateResourceException("username is already existed");
        }

        // validate if email is existed
        if(userRepository.existsByEmail((payload.getEmail()))) {
            throw new DuplicateResourceException("email is already existed");
        }

        User user = mapper.toEntity(payload);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User createdUser = userRepository.save(user);
        String accessToken = jwtUtil.generateToken(createdUser);
        return new AuthResponseDto(accessToken, null);
    }

    public AuthResponseDto login(AuthDto payload) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(payload.getUsername(), payload.getPassword())
        );

        UserDetails userDetails = userService.loadUserByUsername(payload.getUsername());
        String accessToken = jwtUtil.generateToken(userDetails);

        return new AuthResponseDto(accessToken, null);
    }
}
