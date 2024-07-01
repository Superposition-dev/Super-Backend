package com.superposition.canex.controller;

import com.superposition.canex.domain.entity.Email;
import com.superposition.canex.dto.RequestSaveEmail;
import com.superposition.canex.service.EmailService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/canex")
public class CanExController {

    private final EmailService emailService;

    @PostMapping("/emails")
    public ResponseEntity<Email> saveEmail(@Valid @RequestBody RequestSaveEmail req) {
        Email savedEmail = emailService.saveEmail(req);
        return new ResponseEntity<>(savedEmail, HttpStatus.CREATED);
    }
}
