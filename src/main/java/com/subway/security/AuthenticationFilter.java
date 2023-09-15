package com.subway.security;

import com.subway.config.CustomServletRequest;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Component
@Log4j2
public class AuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenUtil tokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        CustomServletRequest customServletRequest = new CustomServletRequest(request);

        Authentication authentication;

        log.info("request client ip[{}]",customServletRequest.getRemoteAddr());
        log.info("request uri[{}]",request.getRequestURI());
        log.info("response code[{}]",response.getStatus());

        String token = resolveToken(request);
        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }
//          validateToken 으로 토큰 유효성 검사
        if (tokenUtil.validateToken(token)) {

//          토큰이 유효할 경우 토큰에서 Authentication 객체를 가지고 와서 SecurityContext 에 저장
            authentication = tokenUtil.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);

//          Authentication 정보와 함께 인증 요청(회원 OR 어드민) 로깅
        }

        filterChain.doFilter(request,response);
    }

    private String resolveToken(HttpServletRequest request) {
        try {
            String bearerToken = request.getHeader("Authorization");
            if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
                return bearerToken.substring(7);
            }
        } catch (IndexOutOfBoundsException ignored) {
        }

        return null;
    }
}
