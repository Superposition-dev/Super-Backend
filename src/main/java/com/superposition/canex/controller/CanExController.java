package com.superposition.canex.controller;

import com.superposition.canex.domain.entity.AccessLog;
import com.superposition.canex.domain.entity.Email;
import com.superposition.canex.domain.mapper.AccessLogMapper;
import com.superposition.canex.dto.RequestSaveEmail;
import com.superposition.canex.service.EmailService;
import com.superposition.exception.CommonErrorCode;
import com.superposition.exception.SuperpositionException;
import java.time.LocalDateTime;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/canex")
public class CanExController {

    private final EmailService emailService;
    private final AccessLogMapper accessLogMapper;

    @PostMapping
    public ResponseEntity<AccessLog> collectLogs(HttpServletRequest request) {
        String ipAddress = request.getRemoteAddr();
        String uri = request.getRequestURI();
        String method = request.getMethod();
        LocalDateTime now = LocalDateTime.now();

        log.info("IP Address: {} | URI: {} | Method: {} | Timestamp: {}", ipAddress, uri, method, now);

        AccessLog accessLog = AccessLog.builder()
                .ipAddress(ipAddress)
                .uri(uri)
                .method(method)
                .now(now)
                .build();
        accessLogMapper.save(accessLog);
        return new ResponseEntity<>(accessLog, HttpStatus.CREATED);
    }

    @PostMapping("/emails")
    public ResponseEntity<Email> saveEmail(@Valid @RequestBody RequestSaveEmail req) {
        try {
            Email savedEmail = emailService.saveEmail(req);
            return new ResponseEntity<>(savedEmail, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new SuperpositionException(CommonErrorCode.CONFLICT);
        }
    }
}
