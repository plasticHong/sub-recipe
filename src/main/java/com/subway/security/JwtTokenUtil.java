package com.subway.security;

import com.subway.dto.LoginResponse;
import com.subway.dto.TokenRefreshResponse;
import com.subway.entity.member.RefreshToken;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Log4j2
@Component
public class JwtTokenUtil {
    private final Key key;

    private final long HALF_HOUR = 1000 * 60 * 30;
    private final long ONE_HOUR = HALF_HOUR * 2;
    private final long ONE_DAY = ONE_HOUR * 24;
    private final long ONE_WEEK = ONE_DAY * 7;
    private final long ONE_MONTH = ONE_WEEK * 4;

    public JwtTokenUtil(@Value("${jwt.token.key}") String tokenKey) {
        byte[] keyBytes = Decoders.BASE64.decode(tokenKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * 로그인 시 토큰 생성
     * */
    public LoginResponse makeToken(Long id, String nickName) {

        long now = (new Date()).getTime();
//        accessToken 유효기간
        final Date accessTokenExpiredTime = new Date(now + HALF_HOUR);
//        refreshToken 유효기간
        final Date refreshTokenExpiredTime = new Date(now + ONE_MONTH);

        String accessToken = Jwts.builder()
                .setSubject(String.valueOf(id))
                .claim("id", id)
                .claim("auth", "ROLE_USER")
                .claim("nickName", nickName)
                .setIssuedAt(new Date())
                .setExpiration(accessTokenExpiredTime)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        String refreshToken = Jwts.builder()
                .claim("id", id)
                .claim("auth", "ROLE_USER")
                .claim("nickName", nickName)
                .setIssuedAt(new Date())
                .setExpiration(refreshTokenExpiredTime)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return LoginResponse.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .message("토큰 생성 성공")
                .memberId(id)
                .build();

    }


    public String getAccessToken(String bearerToken) {
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }
        return null;
    }


    public Map<String, String> getUserInfoFromToken(String Authorization) {

        String accessToken = getAccessToken(Authorization);

        Claims claims = parseClaims(accessToken);

        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("userid", claims.get("userId").toString());

        return userInfo;
    }

    public Authentication getAuthentication(String accessToken) {

        // 토큰 복호화
        Claims claims = parseClaims(accessToken);

        // 클레임에서 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("auth").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        // UserDetails 객체를 만들어서 Authentication 리턴
        UserDetails principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
        } catch (ExpiredJwtException ignore) {
//            log.info("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
        }
        return false;
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public Integer getMemberIdByToken(String refreshToken) {

        Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(refreshToken);
        String idStr = claims.getBody().get("id").toString();
        return Integer.parseInt(idStr);
    }

    public TokenRefreshResponse validateRefreshToken(RefreshToken refreshTokenObj) {

        // refresh 객체에서 refreshToken 추출
        String refreshToken = refreshTokenObj.getRefreshToken();

        try {
            // 검증
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(refreshToken);

            //refresh 토큰의 만료시간이 지나지 않았을 경우, 새로운 access 토큰을 생성합니다.
            if (!claims.getBody().getExpiration().before(new Date())) {
                String id = claims.getBody().get("id").toString();
                String nickName = claims.getBody().get("nickName").toString();

                return reCreationToken(id, nickName);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }
        //refresh 토큰이 만료되었을 경우, 로그인이 필요합니다.
        return null;
    }

    public int toInt(String strNum) {
        return Integer.parseInt(strNum);
    }

    public TokenRefreshResponse reCreationToken(String id,String nickName) {
        Date issuedAt = new Date();
        long now = (issuedAt).getTime();

        final Date accessTokenExpiredTime = new Date(now + HALF_HOUR);
        final Date refreshTokenExpiredTime = new Date(now + ONE_MONTH);

        String refreshToken = Jwts.builder()
                .claim("id", toInt(id))
                .claim("auth", "ROLE_USER")
                .claim("nickName",nickName)
                .setIssuedAt(issuedAt)
                .setExpiration(refreshTokenExpiredTime)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        String accessToken = Jwts.builder()
                .setSubject(id)
                .claim("id", toInt(id))
                .claim("auth", "ROLE_USER")
                .claim("nickName",nickName)
                .setIssuedAt(new Date()) // 토큰 발행 시간 정보
                .setExpiration(accessTokenExpiredTime) // set Expire Time
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
        return
                new TokenRefreshResponse(accessToken,refreshToken);

    }


}
