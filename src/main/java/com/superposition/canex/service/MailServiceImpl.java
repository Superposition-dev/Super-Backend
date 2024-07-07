package com.superposition.canex.service;

import com.superposition.exception.CommonErrorCode;
import com.superposition.exception.SuperpositionException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    @Value("${naver.outboundMailer.endpoint}")
    private String endpoint;
    @Value("${naver.outboundMailer.accessKey}")
    private String accessKey;
    @Value("${naver.outboundMailer.secretKey}")
    private String secretKey;
    private final EmailService emailService;

    // 메일 전송
    @Override
    public void sendMail(RequestSendMail requestSendMail) {
        long currentTimeMillis = System.currentTimeMillis();

        String title = requestSendMail.getTitle();
        String body = requestSendMail.getMessage();

        List<RecipientForRequest> recipients = new ArrayList<>();
        recipients.add(RecipientForRequest.of("수신자 이메일", "수신자")); // emailService에서 저장된 이메일 목록 불러오기

        OutboundMailRequest outboundMailRequest = OutboundMailRequest.of(SENDER_ADDRESS, SENDER_NAME, title, body,
                recipients);
        String result = useWebClient(Long.toString(currentTimeMillis), outboundMailRequest);

        log.info("result = {}", result);
    }

    // webClient 전송
    private String useWebClient(String currentTimeMillis, OutboundMailRequest outboundMailRequest) {
        String requestURI = endpoint + SEND_MAIL_URI;

        Mono<String> result = WebClient.builder().build()
                .post()
                .uri(requestURI)
                .headers(httpHeaders -> {
                    httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                    httpHeaders.add(REQUEST_HEADER_TIMESTAMP, currentTimeMillis);
                    httpHeaders.add(REQUEST_HEADER_ACCESS_KEY, accessKey);
                    httpHeaders.add(REQUEST_HEADER_SIGNATURE,
                            makeSignature(HttpMethod.POST.toString(), SEND_MAIL_URI, currentTimeMillis));
                })
                .body(BodyInserters.fromValue(outboundMailRequest))
                .retrieve()
                .bodyToMono(String.class);

        return result.block();
    }

    // 서명 값 생성
    private String makeSignature(String httpMethod, String url, String timestamp) {
        String message = new StringBuilder()
                .append(httpMethod)
                .append(" ")
                .append(url)
                .append("\n")
                .append(timestamp)
                .append("\n")
                .append(accessKey)
                .toString();

        try {
            SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), SIGNATURE_ALGORITHM);
            Mac mac = Mac.getInstance(SIGNATURE_ALGORITHM);
            mac.init(signingKey);

            byte[] rawHmac = mac.doFinal(message.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(rawHmac);
        } catch (Exception e) {
            log.error("mail send Error", e);
            throw new SuperpositionException(CommonErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
