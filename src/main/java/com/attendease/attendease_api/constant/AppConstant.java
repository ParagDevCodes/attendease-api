package com.attendease.attendease_api.constant;

public class AppConstant {

    public static enum Role {
        ROLE_ADMIN, ROLE_EMPLOYEE
    }

    public static enum TokenStatus {
        VALID, EXPIRED, MALFORMED, UNSUPPORTED, INVALID_SIGNATURE
    }
}
