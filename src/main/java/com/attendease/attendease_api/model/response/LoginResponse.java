package com.attendease.attendease_api.model.response;

import com.attendease.attendease_api.constant.AppConstant;
import com.attendease.attendease_api.constant.CommonApiDataResponse;
import com.attendease.attendease_api.utils.Utils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginResponse extends CommonApiDataResponse {

    private String userId;
    private String token;
    private AppConstant.Role role;
}
