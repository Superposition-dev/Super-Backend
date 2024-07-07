package com.superposition.canex.service;

public interface MailService {

    String SEND_MAIL_URI = "/api/v1/mails";
    String SENDER_ADDRESS = "no_reply@superposition.com";
    String SENDER_NAME = "superposition";
    String REQUEST_HEADER_TIMESTAMP = "x-ncp-apigw-timestamp";
    String REQUEST_HEADER_ACCESS_KEY = "x-ncp-iam-access-key";
    String REQUEST_HEADER_SIGNATURE = "x-ncp-apigw-signature-v2";
    String SIGNATURE_ALGORITHM = "HmacSHA256";

    void sendMail(RequestSendMail requestSendMail);
}
