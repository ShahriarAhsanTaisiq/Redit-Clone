package com.dev.springreditclone.service;

import com.dev.springreditclone.dto.RegisterRequest;

public interface AuthService {
    public void signup(RegisterRequest registerRequest);

    void verifyAccount(String token);
}
