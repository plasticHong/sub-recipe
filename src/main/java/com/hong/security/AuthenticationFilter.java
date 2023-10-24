package com.hong.security;

import com.hong.wrapper.CustomServletRequest;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
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

        if (!tokenUtil.validateToken(token)) {
            filterChain.doFilter(request,response);
            return;
        }

            try {

                authentication = tokenUtil.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
                log.info("Invalid JWT Token", e);
            } catch (ExpiredJwtException e) {
                log.info("Expired JWT Token", e);
                response.setStatus(401);
            } catch (UnsupportedJwtException e) {
                log.info("Unsupported JWT Token", e);
            } catch (IllegalArgumentException e) {
                log.info("JWT claims string is empty.", e);
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
