package com.boot.api.domain.user.entity;

import com.boot.api.globals.common.enums.Role;
import com.boot.api.globals.common.enums.UserStatus;
import com.boot.api.globals.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String userName;
    private String userEmail;
    @JsonIgnore
    private String userPassword;
    private String phone;
    private Integer groupId;
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;
    @Enumerated(EnumType.STRING)
    private Role role;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "groupId", insertable = false, updatable = false)
    private UserGroup userGroup;

    @Builder
    public User(Integer id, String userName, String userEmail, String userPassword, String phone, Integer groupId, UserStatus userStatus, Role role, UserGroup userGroup) {
        this.id = id;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.phone = phone;
        this.groupId = groupId;
        this.userStatus = userStatus;
        this.role = role;
        this.userGroup = userGroup;
    }

    public void disabledUser() {
        this.userStatus = UserStatus.INACTIVE;
    }
    public void deletedUser() { this.userStatus = UserStatus.DELETED; }
}
