package com.subway.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.util.Arrays;

public class CustomServletRequest extends HttpServletRequestWrapper {
    public CustomServletRequest(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getRemoteAddr() {
        return Arrays.stream(IP_HEADER.values())
                .map(header->getHeader(header.headerName))
                .filter(ip->ip !=null && !ip.isBlank())
                .map(ip->ip.split(",")[0].trim())
                .findFirst()
                .orElseGet(super::getRemoteAddr);
    }

    enum IP_HEADER{
        X_REAL_IP("X-Real-IP"),
        X_FORWARDED_ID("X-Forwarded-For");

        private final String headerName;

        IP_HEADER(String headerName) {
            this.headerName=headerName;
        }

    }

}
