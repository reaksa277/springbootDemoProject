package com.reaksa.demo.service.security;

import com.reaksa.demo.entity.RefreshToken;
import com.reaksa.demo.entity.User;
import com.reaksa.demo.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class RefreshTokenService {
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    public RefreshToken createRefreshToken(User user) {
        String refreshToken = UUID.randomUUID().toString();

        RefreshToken entity = new RefreshToken();
        entity.setToken(refreshToken);
        entity.setExpiredAt(LocalDateTime.now().plusDays(3));
        entity.setUser(user);

        return refreshTokenRepository.save(entity);
    }
}
