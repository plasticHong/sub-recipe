package com.hong.utils;

import com.hong.exception.CustomAuthException;
import com.hong.wrapper.CustomServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
public class AuthenticationUtils {

    public static Long getCurrentMemberId() {

        String idStr = SecurityContextHolder.getContext().getAuthentication().getName();

        try {

            if (idStr.trim().equalsIgnoreCase("anonymousUser")) {
                log.info("anonymous user!");
                return null;
            }

            return Long.parseLong(idStr);
        } catch (NumberFormatException e) {
            throw new CustomAuthException("check your token");
        }

    }

    public static String getUserIp() {

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (requestAttributes != null) {

            HttpServletRequest request = requestAttributes.getRequest();

            return new CustomServletRequest(request).getRemoteAddr();
        }

        return null;
    }

}
