package com.test1.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test1.domain.dto.token.TokenInfo;
import com.test1.domain.dto.user.UserRole;
import com.test1.domain.entity.user.User;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtUtil {

    @Value("${jwt.token.secret}")
    private String secretKey;

    private final long accessTokenValidTime = 1000L * 60 * 60; // 1시간
    private final long refreshTokenValidTime = accessTokenValidTime * 24 * 7; // 7일
    private final String ID_KEY = "id";
    private final String ROLE_KEY = "role";
    private final String USERID_KEY = "userId";

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    // Claims 추출 메서드
    private static Claims extractClaims(String token, String secretKey) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    // token에서 userId 추출
    public static Long getUserId(String token) throws JsonProcessingException {
        String payload = token.split("\\.")[1];

        Base64.Decoder decoder = Base64.getDecoder();
        payload = new String(decoder.decode(payload.getBytes()));

        ObjectMapper mapper = new ObjectMapper();
        Map map = mapper.readValue(payload, Map.class);

        return Long.parseLong(map.get("userId").toString());
    }

    // token의 유효 및 만료 확인
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.error("잘못된 JWT signature");
            return false;
        } catch (UnsupportedJwtException e) {
            log.error("지원하지 않는 JWT");
            return false;
        } catch (IllegalArgumentException e) {
            log.error("잘못된 JWT");
            return false;
        } catch (ExpiredJwtException e) {
            log.error("JWT 만료");
            return false;
        }
    }

    // token 생성
    public TokenInfo createToken(String userId, UserRole role, Long id){
        Claims claims = Jwts.claims();
        claims.put("userId", userId);
        claims.put("userRole", role);
        claims.put("id", id);

        String accessToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenValidTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        String refreshToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenValidTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        return TokenInfo.builder()
                .grantType("bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .refreshTokenExpireTime(refreshTokenValidTime)
                .build();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        Long id = Long.parseLong(claims.get(ID_KEY).toString());
        String userId = claims.get(USERID_KEY).toString();
        String roleName = claims.get(ROLE_KEY).toString();

        User user = User.builder()
                .id(id)
                .userId(userId)
                .role(UserRole.ROLE_USER)
                .build();
        return new UsernamePasswordAuthenticationToken(user, token, user.getAuthorities());
    }

}
