package com.attendease.attendease_api.security;

import com.attendease.attendease_api.model.Users;
import com.attendease.attendease_api.repository.UsersRepository;
import com.attendease.attendease_api.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsersRepository usersRepository; // Your JPA repository

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException(userId));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getId())
                .password(user.getPassword()) // already encoded
                .authorities(user.getRole().toString())   // example: "ROLE_ADMIN"
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService(CustomUserDetailsService customService) {
        return customService;
    }
}
