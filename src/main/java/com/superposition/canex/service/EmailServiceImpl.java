package com.superposition.canex.service;

import com.superposition.canex.domain.entity.Email;
import com.superposition.canex.domain.mapper.EmailMapper;
import com.superposition.canex.dto.RequestSaveEmail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final EmailMapper emailMapper;

    @Override
    public Email saveEmail(RequestSaveEmail req) {
        Email emailEntity = Email.builder()
                .email(req.getEmail())
                .registrationDateTime(req.getRegistrationDateTime())
                .build();
        emailMapper.save(emailEntity);
        return emailEntity;
    }
}
