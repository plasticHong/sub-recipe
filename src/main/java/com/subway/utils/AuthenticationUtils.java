package com.subway.utils;

import com.subway.exception.CustomAuthException;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthenticationUtils {

    public static Long getCurrentMemberId() {
        String idStr = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            return Long.parseLong(idStr);
        } catch (NumberFormatException e) {
            throw new CustomAuthException("check your token");
        }
    }

}
