package com.test1.domain.dto.token;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenInfo {

    private String grantType;
    private String accessToken;
    private String refreshToken;
    private long refreshTokenExpireTime;

}
