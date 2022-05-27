package com.edu.signup.jwt;

import com.edu.signup.config.AesEncryptor;
import com.edu.signup.model.dto.login.UserSignInRes;
import com.fasterxml.jackson.databind.ser.Serializers.Base;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.annotations.Authorization;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.util.WebUtils;

@RequiredArgsConstructor
@Component
@Getter
public class JwtTokenProvider {

    private static String secretKey = "testest";

    private long tokenValidTime = 30 * 60 * 1000L;
    private long refreshValidTime = 1 * 60 * 2000L;

    private final UserDetailsService userDetailsService;

    @PostConstruct
    protected void init(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String createToken(UserSignInRes loginUser){
        Claims claims = Jwts.claims().setSubject(loginUser.getUserEmail());
        claims.put("roles", loginUser.getUserRole());
        Date now = new Date();
        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(new Date(now.getTime() + tokenValidTime))
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact();
    }

    public Map<String, String> createRefreshToken(UserSignInRes loginUser){
        Map<String, Object> headers = new HashMap<>();
        headers.put("type", "token");

        Map<String, Object> payloads = new HashMap<>();
        payloads.put("email", loginUser.getUserEmail());
        payloads.put("role", loginUser.getUserRole());

        Date now = new Date();

        String jwt = Jwts.builder()
            .setHeader(headers)
            .setClaims(payloads)
            .setExpiration(new Date(now.getTime() + refreshValidTime))
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact();

        Map<String, String> result = new HashMap<>();
        result.put("refreshToken", jwt);
        return result;
    }

    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token){
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰에서 회원 정보 추출
    private String getUserPk(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    // Request의 Header에서 token값을 가져오기 "X-AUTH-TOKEN" : "TOKEN값"
    public String resolveToken(HttpServletRequest request){
        //return request.getHeader("X-AUTH-TOKEN");
        String bearerToken = request.getHeader("Authorization");
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        return null;
    }


    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken){
        Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
        return !claims.getBody().getExpiration().before(new Date());
    }



}
