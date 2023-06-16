package com.test1.domain.dto.user;

import com.test1.domain.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class UserJoinRequest {

    private String userId;
    private String password;

    public User toEntity(String password) {
        return User.builder()
                .userId(this.userId)
                .password(password)
                .role(UserRole.ROLE_USER)
                .build();
    }

}
