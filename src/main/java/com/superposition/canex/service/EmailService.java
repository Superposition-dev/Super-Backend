package com.superposition.canex.service;

import com.superposition.canex.domain.entity.Email;
import com.superposition.canex.dto.RequestSaveEmail;

public interface EmailService {

    Email saveEmail(RequestSaveEmail req);
}
