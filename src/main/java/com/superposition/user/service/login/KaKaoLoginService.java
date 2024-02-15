package com.superposition.user.service.login;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.superposition.user.dto.UserInfo;
import com.superposition.user.exception.ApiCallFailedException;
import com.superposition.user.exception.InvalidTokenException;
import com.superposition.user.exception.ParsingException;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class KaKaoLoginService implements OAuthLoginService {
    @Value("${spring.security.oauth2.client.registration.kakao-auth-uri}")
    private String KAKAO_AUTH_URI;
    @Value("${spring.security.oauth2.client.registration.kakao_profile_uri}")
    private String KAKAO_PROFILE_URI;
    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String KAKAO_CLIENT_ID;
    @Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
    private String KAKAO_CLIENT_SECRET;
    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String KAKAO_REDIRECT_URL;
    @Value("${naver.objectStorage.bucketName}")
    private String bucketName;

    private final AmazonS3 awsS3Client;

    @Override
    public String getAccessTokenByCode(String code) {
        if (code.isBlank()) throw new InvalidTokenException();

        try {
            //헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            //전달 데이터 설정
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("grant_type", "authorization_code");
            params.add("client_id", KAKAO_CLIENT_ID);
            params.add("client_secret", KAKAO_CLIENT_SECRET);
            params.add("code", code);
            params.add("redirect_uri", KAKAO_REDIRECT_URL);

            //데이터, 헤더를 http
            HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);

            //위에 데이터를 http로 변환하여 카카오 주소로 토큰 요청
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(
                    KAKAO_AUTH_URI,
                    HttpMethod.POST,
                    httpEntity,
                    String.class);

            //응답 데이터 중 토큰 획득
            return getTokenFromResponse(response.getBody());
        } catch (HttpClientErrorException he) {
            throw new InvalidTokenException();
        } catch (Exception e) {
            throw new ApiCallFailedException();
        }
    }

    @Override
    public UserInfo getUserInfoByToken(String accessToken) {
        try {
            //헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(accessToken);
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            //데이터 전송
            RestTemplate rt = new RestTemplate();
            HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(headers);
            ResponseEntity<String> response = rt.exchange(
                    KAKAO_PROFILE_URI,
                    HttpMethod.POST,
                    httpEntity,
                    String.class
            );
            //Response 데이터 파싱
            return jsonToDTO(response.getBody());
        } catch (HttpClientErrorException he) {
            throw new InvalidTokenException();
        } catch (Exception e) {
            throw new ApiCallFailedException();
        }
    }

    private String getTokenFromResponse(String response) {
        JSONParser jsonParser = new JSONParser();
        try {
            JSONObject responseObj = (JSONObject) jsonParser.parse(response);
            return (String) responseObj.get("access_token");
        } catch (ParseException ex) {
            throw new ParsingException();
        }
    }

    private UserInfo jsonToDTO(String response) {
        JSONParser jsonParser = new JSONParser();
        try {
            JSONObject responseObj = (JSONObject) jsonParser.parse(response);
            JSONObject kakaoAccount = (JSONObject) responseObj.get("kakao_account");
            JSONObject profile = (JSONObject) kakaoAccount.get("profile");

            String email = String.valueOf(kakaoAccount.get("email"));
            String nickname = String.valueOf(profile.get("nickname"));
            String imageUrl = String.valueOf(profile.get("profile_image_url"));

            String profileImage = uploadImageToStorage(imageUrl);

            return UserInfo.builder().
                    email(email).
                    nickname(nickname).
                    profile(profileImage).build();
        } catch (ParseException e) {
            throw new ParsingException();
        }
    }

    private String uploadImageToStorage(String imageUrl) {
        String extension = imageUrl.substring(imageUrl.length() - 4);
        String imageName = setImageName(extension);

        try (InputStream in = URI.create(imageUrl).toURL().openStream()) {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType("image/jpeg");

            awsS3Client.putObject(bucketName, imageName, in, metadata);
            return imageName;
        } catch (IOException e) {
            throw new RuntimeException("이미지 업로드 중 에러");
        }
    }

    private String setImageName(String extension){
        return UUID.randomUUID() + extension;
    }
}