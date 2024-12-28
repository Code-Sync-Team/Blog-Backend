package code_sync_team.blog.user.application.auth.oauth.kakao;

import code_sync_team.blog.user.application.auth.oauth.OAuthService;
import code_sync_team.blog.user.application.auth.oauth.kakao.dto.KaKaoUserInfoResponseDto;
import code_sync_team.blog.user.application.auth.oauth.kakao.dto.KakaoTokenResponseDto;
import code_sync_team.blog.user.domain.AuthType;
import code_sync_team.blog.user.domain.User;
import code_sync_team.blog.user.domain.UserRepository;
import code_sync_team.blog.user.infra.auth.UserJwtProvider;
import io.netty.handler.codec.http.HttpHeaderValues;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KakaoOauthService implements OAuthService {

    @Value("${security.kakao.url}")
    private String KAKAO_URL;

    @Value("${security.kakao.key}")
    private String KAKAO_KEY;

    @Value("${security.kakao.redirect_uri}")
    private String REDIRECT_URI;

    private final UserJwtProvider userJwtProvider;
    private final UserRepository userRepository;

    private static final String CODE = "code";
    private static final String RESPONSE_TYPE_PREFIX = "response_type";
    private static final String CLIENT_ID_PREFIX = "client_id";
    private static final String REDIRECT_URI_PREFIX = "redirect_uri";


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
    public String login(String code) {
        String email = getUserEmail(code);
        User user = userRepository.findByEmailAndAuthType(email, AuthType.KAKAO).orElseGet(
                () -> userRepository.save(User.createByEmailWithAuthType(email, AuthType.KAKAO))
        );

        return userJwtProvider.createToken(user.getId());
    }

    private String getUserEmail(String code) {
        String accessToken = getAccessToken(code);

        KaKaoUserInfoResponseDto userInfo = WebClient.create("https://kapi.kakao.com")
                .get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .path("/v2/user/me")
                        .build(true))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .header(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new RuntimeException("Invalid Parameter")))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> Mono.error(new RuntimeException("Internal Server Error")))
                .bodyToMono(KaKaoUserInfoResponseDto.class)
                .block();

        return userInfo.kakaoAccount.email;
    }

    private String getAccessToken(String code) {
        KakaoTokenResponseDto kakaoTokenResponseDto = WebClient.create("https://kauth.kakao.com").post()
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

        return kakaoTokenResponseDto.getAccessToken();
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
