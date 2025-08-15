package com.attendease.attendease_api.service.impl;

import com.attendease.attendease_api.model.Users;
import com.attendease.attendease_api.model.request.LoginRequest;
import com.attendease.attendease_api.model.response.LoginResponse;
import com.attendease.attendease_api.repository.UsersRepository;
import com.attendease.attendease_api.service.AuthService;
import com.attendease.attendease_api.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UsersRepository usersRepository;
    private final JwtUtils jwtUtils;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        LoginResponse loginResponse = new LoginResponse();

        Optional<Users> userDetailsOptional = usersRepository.findByUserName(loginRequest.getUsername());

        if (userDetailsOptional.isEmpty()){
            loginResponse.setCheckValidationFailed(true);
            loginResponse.setValidationMessage("Invalid Username");
            return loginResponse;
        }

        Users userDetails = userDetailsOptional.get();

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if (passwordEncoder.matches(loginRequest.getPassword(), userDetails.getPassword())){
            return generateJwtTokenResponse(userDetails);
        } else {
            loginResponse.setCheckValidationFailed(true);
            loginResponse.setValidationMessage("Invalid Password");
            return loginResponse;
        }

    }

    private LoginResponse generateJwtTokenResponse(Users userDetails){
        LoginResponse loginResponse = new LoginResponse();
        String token = jwtUtils.generateToken(userDetails);
        loginResponse.setToken(token);
        loginResponse.setRole(userDetails.getRole());
        loginResponse.setUserId(userDetails.getId());
        loginResponse.setMessage("Authentication successful");
        return loginResponse;
    }
}
