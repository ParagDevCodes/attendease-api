package com.attendease.attendease_api.model;

import com.attendease.attendease_api.utils.Utils;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
@SuperBuilder
public class Users {

    @Id
    @Builder.Default
    private String id = Utils.generateUUID();

    @Column(name = "username", nullable = false, unique = true)
    private String userName;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Utils.Role role;

    @Builder.Default
    @Column(name = "created_at")
    private Long created_at = Instant.now().getEpochSecond();
}
