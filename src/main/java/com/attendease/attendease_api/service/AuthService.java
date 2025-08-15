package com.attendease.attendease_api.service;

import com.attendease.attendease_api.model.request.LoginRequest;
import com.attendease.attendease_api.model.response.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest loginRequest);
}
