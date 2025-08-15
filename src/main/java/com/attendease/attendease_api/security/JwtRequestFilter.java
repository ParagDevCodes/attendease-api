package com.attendease.attendease_api.security;

import com.attendease.attendease_api.constant.APIRequestURL;
import com.attendease.attendease_api.constant.AppConstant;
import com.attendease.attendease_api.utils.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        // BY PASS OPEN APIS
        if (Arrays.asList(APIRequestURL.byPassPostEndPoints).contains(request.getRequestURI())){
            filterChain.doFilter(request,response);
            return;
        }

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            sendError(response, "Missing or invalid Authorization header");
            return;
        }

        String jwtToken = authHeader.substring(7); // remove "Bearer "
        JwtUtils.TokenValidationResult validationResult = jwtUtils.validateToken(jwtToken);

        if (validationResult.status() != AppConstant.TokenStatus.VALID) {
            sendError(response, "Invalid token: " + validationResult.status().name());
            return;
        }

        String userId = validationResult.claims().getSubject();
        if (userId == null) {
            sendError(response, "Token does not contain a valid subject (user ID)");
            return;
        }

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userId);

            UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        filterChain.doFilter(request,response);

    }

    private void sendError(HttpServletResponse response, String message) throws IOException {
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("resultCode", "ERROR");
        resultMap.put("resultMessage", message);

        Map<String,Object> responseMap = new HashMap<>();
        responseMap.put("result", resultMap);

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getOutputStream(), responseMap);
    }
}
