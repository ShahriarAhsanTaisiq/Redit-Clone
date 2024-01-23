package com.dev.springreditclone.service;

import com.dev.springreditclone.dto.AuthenticationResponse;
import com.dev.springreditclone.dto.LoginRequest;
import com.dev.springreditclone.dto.RegisterRequest;

public interface AuthService {
    public void signup(RegisterRequest registerRequest);

    void verifyAccount(String token);

    AuthenticationResponse login(LoginRequest loginRequest);
}
