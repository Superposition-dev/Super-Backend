package com.superposition.canex.domain.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AccessLog {

    private final String ipAddress;
    private final String uri;
    private final String method;
    private final LocalDateTime now;

    @Builder
    public AccessLog(String ipAddress, String uri, String method, LocalDateTime now) {
        this.ipAddress = ipAddress;
        this.uri = uri;
        this.method = method;
        this.now = now;
    }

    public LocalDate getRegistrationDate() {
        return now.toLocalDate();
    }
}
