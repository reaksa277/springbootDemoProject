package com.reaksa.demo.service.security;

import com.reaksa.demo.common.config.ApplicationConfiguration;
import com.reaksa.demo.entity.RefreshToken;
import com.reaksa.demo.entity.User;
import com.reaksa.demo.exception.model.ResourceNotFoundException;
import com.reaksa.demo.repository.RefreshTokenRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.UUID;

@Service
public class RefreshTokenService {
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private ApplicationConfiguration appConfig;
    private long expiration;

    @PostConstruct
    private void init() {
        this.expiration = appConfig.getSecurity().getExpiration();
    }

    public RefreshToken createRefreshToken(User user) {
        String refreshToken = UUID.randomUUID().toString();

        RefreshToken entity = new RefreshToken();
        entity.setToken(refreshToken);
        entity.setExpiredAt(LocalDateTime.now().plusDays(expiration));
        entity.setUser(user);

        return refreshTokenRepository.save(entity);
    }

    public RefreshToken findByToken(String token) {
        return refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new ResourceNotFoundException("Refresh token is invalid"));
    }

    public RefreshToken verifyToken(RefreshToken token) throws AuthenticationException {
        // expiration && revoke
        if(!token.isValid()) {
            refreshTokenRepository.delete(token);
            throw new AuthenticationException("Refresh token is expired or revoke");
        }

        return token;
    }

    public RefreshToken rotateRefreshToken(RefreshToken oldToken) {
        // revoke old refresh token
        oldToken.setRevoked(true);
        refreshTokenRepository.save(oldToken);

        return this.createRefreshToken(oldToken.getUser());
    }

    public String generateSecureRefreshToken() {
        // -128 to 127
        SecureRandom random = new SecureRandom();

        // array bytes of 64 lenght , 512 bits
        byte[] tokenBytes = new byte[64];

        // make each bytes has its own secure value
        // [67,-125,100,12,....]
        random.nextBytes(tokenBytes);

        // "hwdj1e0slasf3f3/+asjdfasjd/+" not URL friendly
        // "hashfafej_-asdfkjake" URL friendly
        return Base64.getUrlEncoder().withoutPadding().encodeToString(tokenBytes);
    }
}
