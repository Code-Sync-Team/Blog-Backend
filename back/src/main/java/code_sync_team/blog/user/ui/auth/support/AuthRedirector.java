package code_sync_team.blog.user.ui.auth.support;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AuthRedirector {
    private String kakaoKey = "fc7d32f457e06accaef6046e4ef2032a";
    private String redirectUrl = "http://localhost:8080/login/oauth2/code/kakao";


    public void redirect(HttpServletResponse response, String code) {
        String redirectUrl = getRedirectUrl(code);
        try {
            response.sendRedirect(redirectUrl);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getRedirectUrl(String provider) {
        if (provider.equals("kakao")) {
            return "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=" + kakaoKey + "&redirect_uri=" + redirectUrl;
        }

        else
            return null;
    }
}
