package com.attendease.attendease_api.controller;

import com.attendease.attendease_api.constant.APIRequestURL;
import com.attendease.attendease_api.model.request.LoginRequest;
import com.attendease.attendease_api.model.response.LoginResponse;
import com.attendease.attendease_api.service.AuthService;
import com.attendease.attendease_api.utils.Utils;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(APIRequestURL.apiBaseUrl+"auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(value = APIRequestURL.loginUrl)
    public ResponseEntity<JsonNode> login(@RequestHeader HttpHeaders httpHeaders, @RequestBody LoginRequest loginRequest){
        if (loginRequest.checkBadRequest()){
            return new ResponseEntity<>(Utils.generateErrorResponse("Bad Request"), HttpStatus.BAD_REQUEST);
        }
        LoginResponse loginResponse = authService.login(loginRequest);
        if (loginResponse.getCheckValidationFailed()){
            return ResponseEntity.ok(Utils.generateErrorResponse(loginResponse.getValidationMessage()));
        }
        return ResponseEntity.ok(Utils.generateSuccessResponse(loginResponse,loginResponse.getMessage()));
    }

    @GetMapping(value = "getUsers")
    public ResponseEntity<JsonNode> getUsers(@RequestHeader HttpHeaders httpHeaders){
        return ResponseEntity.ok(Utils.generateErrorResponse("get user details"));
    }
}
