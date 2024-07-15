package com.superposition.canex.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RequestSaveEmail {

//    @Email
    private String email;
//    @NotNull
    private LocalDateTime registrationDateTime;

    @Builder
    public RequestSaveEmail(String email, LocalDateTime registrationDateTime) {
        this.email = email;
        this.registrationDateTime = registrationDateTime;
    }
}
