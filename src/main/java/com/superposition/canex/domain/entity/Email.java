package com.superposition.canex.domain.entity;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Email {

    private final String email;
    private final LocalDateTime registrationDateTime;

    @Builder
    public Email(String email, LocalDateTime registrationDateTime) {
        this.email = email;
        this.registrationDateTime = registrationDateTime;
    }
}
