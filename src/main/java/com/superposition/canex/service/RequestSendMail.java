package com.superposition.canex.service;

import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestSendMail {

    @NotBlank
    private String title;
    @NotBlank
    private String message;

    @Builder
    public RequestSendMail(String title, String message) {
        this.title = title;
        this.message = message;
    }
}
