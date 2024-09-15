package com.boot.api.domain.user.entity;

import com.boot.api.globals.common.enums.UserStatus;
import com.boot.api.globals.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String userEmail;
    @JsonIgnore
    private String userPassword;
    private String phone;
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;
}
