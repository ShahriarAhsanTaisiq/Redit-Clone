package com.dev.springreditclone.service;

import com.dev.springreditclone.dto.RegisterRequest;
import com.dev.springreditclone.model.User;
import com.dev.springreditclone.model.VerificationToken;
import com.dev.springreditclone.repository.UserRepository;
import com.dev.springreditclone.repository.VerificationTokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
    private final   PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;

    @Override
    @Transactional
    public void signup(RegisterRequest registerRequest) {
        User user =  new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreated(Instant.now());
        user.setEnabled(false);
        userRepository.save(user);
        
        String token  = generateVerificationToken(user);
    }

    private String generateVerificationToken(User user) {
        String token =  UUID.randomUUID().toString();

        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);

        verificationTokenRepository.save(verificationToken);
        return token;

    }
}
