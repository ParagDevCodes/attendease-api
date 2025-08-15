package com.attendease.attendease_api.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.StringUtils;

@Getter
@Setter
@NoArgsConstructor
public class LoginRequest {

    private String username;
    private String password;

    public Boolean checkBadRequest(){
        return StringUtils.isEmpty(username) || StringUtils.isEmpty(password);
    }
}
