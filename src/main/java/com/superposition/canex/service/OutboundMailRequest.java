package com.superposition.canex.service;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OutboundMailRequest {

    private String senderAddress; // 발송자 Email 주소
    private String senderName; // 발송자 이름
    private String body; // Email 본문
    private String title; // Mail 제목
    private List<RecipientForRequest> recipients; // 수신자 목록
    private boolean individual = true; // 개인별 발송 여부, 일반 발송 여부
    private boolean advertising = false; // 광고 메일 여부

    protected OutboundMailRequest(String senderAddress, String senderName, String title, String body,
                                  List<RecipientForRequest> recipients) {
        this.senderAddress = senderAddress;
        this.senderName = senderName;
        this.title = title;
        this.body = body;
        this.recipients = recipients;
    }

    protected OutboundMailRequest(String senderAddress, String senderName, String title, String body,
                                  List<RecipientForRequest> recipients, boolean individual, boolean advertising) {
        this.senderAddress = senderAddress;
        this.senderName = senderName;
        this.title = title;
        this.body = body;
        this.recipients = recipients;
        this.individual = individual;
        this.advertising = advertising;
    }

    public static OutboundMailRequest of(String senderAddress, String senderName, String title, String body,
                                         List<RecipientForRequest> recipients) {
        return new OutboundMailRequest(senderAddress, senderName, title, body, recipients);
    }

    public static OutboundMailRequest of(String senderAddress, String senderName, String title, String body,
                                         List<RecipientForRequest> recipients, boolean individual, boolean advertising) {
        return new OutboundMailRequest(senderAddress, senderName, title, body, recipients, individual, advertising);
    }
}
