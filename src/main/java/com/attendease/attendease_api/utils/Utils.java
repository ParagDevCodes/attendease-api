package com.attendease.attendease_api.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Utils {

    public static enum Role {
        ADMIN, EMPLOYEE
    }

    public static String generateUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }

    public static String createHashedPassword(String password){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        return hashedPassword;
    }

    public static JsonNode generateSuccessResponse(Object object, String message){
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("resultCode", "SUCCESS");
        resultMap.put("resultMessage", message);

        Map<String,Object> responseMap = new HashMap<>();
        responseMap.put("result", resultMap);
        responseMap.put("data", object);

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.valueToTree(responseMap);
    }

    public static JsonNode generateErrorResponse(String message){
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("resultCode", "ERROR");
        resultMap.put("resultMessage", message);

        Map<String,Object> responseMap = new HashMap<>();
        responseMap.put("result", resultMap);

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.valueToTree(responseMap);
    }

    public static void main(String[] args) {
        System.out.println(generateUUID());;
        System.out.println("0000 -> " + createHashedPassword("0000"));
    }
}
