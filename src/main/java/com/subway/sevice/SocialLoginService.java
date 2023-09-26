package com.subway.sevice;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.subway.dto.LoginResponse;
import com.subway.dto.Request.NicknameRegistrationRequest;
import com.subway.entity.member.Member;
import com.subway.repository.MemberRepo;
import com.subway.security.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SocialLoginService {

    @Value("${kakao.api.key}")
    private String key;
    private final String socialIdPrefix = "kakao_";
    private final String domain = "http://localhost:8080";
    private final String redirectUri = domain +
            "/oauth/login/kakao/code";

    private final MemberRepo memberRepo;
    private final JwtTokenUtil tokenMaker;
    private final UserAuthenticationService authenticationService;

    @Transactional
    public LoginResponse kakaoLogin(String code) {

        String token = getTokenByCode(code);
        String kakaoId = getKakaoIdByToken(token);
        String socialId = socialIdPrefix + kakaoId;


        LoginResponse loginResponse = loginSocialMember(socialId);

        if (loginResponse == null) {
            Long memberId = saveKakaoMember(socialId);
            return new LoginResponse(memberId);
        }

        return loginResponse;
    }

    @Transactional
    public void nicknameRegistration(NicknameRegistrationRequest request) {
        Long memberId = request.getMemberId();
        Member member = memberRepo.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("memberId {" + memberId + "}"));

        member.nicknameRegistration(request.getNickname());
    }

    public LoginResponse loginSocialMember(String socialId) {

        Optional<Member> byUserId = memberRepo.findByUserId(socialId);

        if (byUserId.isPresent()) {
            Member member = byUserId.get();

            if (member.getNickName()==null){
                return new LoginResponse(member.getId());
            }

            LoginResponse loginResponse = tokenMaker.makeToken(member.getId(), member.getNickName());
            authenticationService.refreshTokenSave(member.getUserId(), loginResponse.getRefreshToken());
            return loginResponse;
        }

        return null;
    }

    public Long saveKakaoMember(String socialId) {

        Member member = new Member(socialId);

        return memberRepo.save(member).getId();
    }

    private String getTokenByCode(String code) {

        RestTemplate restTemplate = new RestTemplate();

        String url = "https://kauth.kakao.com/oauth/token";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();

        requestBody.add("grant_type", "authorization_code");
        requestBody.add("client_id", key);
        requestBody.add("redirect_uri", redirectUri);
        requestBody.add("code", code);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestEntity, String.class);

        HttpStatusCode statusCode = responseEntity.getStatusCode();
        String body = responseEntity.getBody();

        String accessToken = null;
        if (statusCode.is2xxSuccessful() && body != null) {
            JsonElement jsonElement = JsonParser.parseString(body);
            accessToken = jsonElement.getAsJsonObject().get("access_token").getAsString();
        }
        return accessToken;

    }


    private String getKakaoIdByToken(String token) {

        String url = "https://kapi.kakao.com/v1/user/access_token_info";

        ResponseEntity<String> responseEntity = sendToken(token, url);

        HttpStatusCode statusCode = responseEntity.getStatusCode();
        String body = responseEntity.getBody();

        if (statusCode.is2xxSuccessful() && body != null) {

            JsonObject asJsonObject = JsonParser.parseString(body).getAsJsonObject();
            String id = asJsonObject.get("id").getAsString();
            return id;
        } else {
            System.out.println("somethings wrong... status code : " + statusCode);
        }
        return null;
    }

    private ResponseEntity<String> sendToken(String token, String url) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        headers.add("Authorization", "Bearer " + token);

        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);

        return restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
    }

}
