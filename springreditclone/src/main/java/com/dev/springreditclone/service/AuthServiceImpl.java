package com.dev.springreditclone.service;

import com.dev.springreditclone.dto.AuthenticationResponse;
import com.dev.springreditclone.dto.LoginRequest;
import com.dev.springreditclone.dto.RegisterRequest;
import com.dev.springreditclone.exception.SpringReditException;
import com.dev.springreditclone.model.NotificationEmail;
import com.dev.springreditclone.model.User;
import com.dev.springreditclone.model.VerificationToken;
import com.dev.springreditclone.repository.UserRepository;
import com.dev.springreditclone.repository.VerificationTokenRepository;
import com.dev.springreditclone.security.JwtProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
    private final   PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailServiceImpl mailService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;

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
        mailService.sendMail(new NotificationEmail("Please activate your account",
                user.getEmail(),"Thank you for signing up to Spring Reddit, " +
                "please click on the below url to activate your account : " +
                "http://localhost:8080/api/auth/accountVerification/" + token));
    }

    @Override
    public void verifyAccount(String token) {
      Optional<VerificationToken> verificationToken= verificationTokenRepository.findByToken(token);
      verificationToken.orElseThrow(()->new SpringReditException("Invalid Token"));
      fetchUserAndEnable(verificationToken.get());
    }

    @Override
    public AuthenticationResponse login(LoginRequest loginRequest) {
       Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtProvider.generateToken(authenticate);
        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenService.generateRefreshToken().getToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(loginRequest.getUsername())
                .build();

    }

    @Transactional
    public void fetchUserAndEnable(VerificationToken verificationToken) {
       String username= verificationToken.getUser().getUsername();
       User user = userRepository.findByUsername(username).orElseThrow(()->
               new SpringReditException("User not found with name " + username));
       user.setEnabled(true);
       userRepository.save(user);
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
