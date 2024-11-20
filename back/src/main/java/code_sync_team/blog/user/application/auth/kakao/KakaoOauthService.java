package code_sync_team.blog.user.application.auth.kakao;

import code_sync_team.blog.user.application.auth.OAuthService;
import code_sync_team.blog.user.application.auth.kakao.dto.KakaoTokenResponseDto;
import io.netty.handler.codec.http.HttpHeaderValues;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Service
public class KakaoOauthService implements OAuthService {

    private static String KAKAO_URL = "https://kauth.kakao.com/oauth/authorize";
    private static String CODE = "CODE";


    private static String RESPONSE_TYPE_PREFIX = "response_type";
    private static String CLIENT_ID_PREFIX = "client_id";
    private static String REDIRECT_URI_PREFIX = "redirect_uri";

    private String KAKAO_KEY = "fc7d32f457e06accaef6046e4ef2032a";
    private String REDIRECT_URI = "http://localhost:8080/login/oauth2/code/kakao";

    @Override
    public void redirect(HttpServletResponse response) {
        String redirectUrl = getRedirectUrl();
        try {
            response.sendRedirect(redirectUrl);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getUserEmail(String accessToken) {

    }

    @Override
    public String getAccessToken(String code) {
        KakaoTokenResponseDto kakaoTokenResponseDto = WebClient.create().post()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .path("/oauth/token")
                        .queryParam("grant_type", "authorization_code")
                        .queryParam(CLIENT_ID_PREFIX, KAKAO_KEY)
                        .queryParam(CODE, code)
                        .build(true))
                .header(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString())
                .retrieve()
                //TODO : Custom Exception
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new RuntimeException("Invalid Parameter")))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> Mono.error(new RuntimeException("Internal Server Error")))
                .bodyToMono(KakaoTokenResponseDto.class)
                .block();


    }

    private String getRedirectUrl() {
        return UriComponentsBuilder
                .fromHttpUrl(KAKAO_URL)
                .queryParam(RESPONSE_TYPE_PREFIX, CODE)
                .queryParam(CLIENT_ID_PREFIX, KAKAO_KEY)
                .queryParam(REDIRECT_URI_PREFIX, REDIRECT_URI)
                .build()
                .toUriString();
    }
}
