package code_sync_team.blog.user.ui.auth.oauth;

import code_sync_team.blog.user.application.auth.oauth.OAuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserOAuthController {

    private final OAuthService kakaoOAuthService;

    @GetMapping("/oauth/login/{provider}")
    public void oAuthLogin(@PathVariable String provider, HttpServletResponse response) {
        if (provider.equals("kakao")) kakaoOAuthService.redirect(response);
    }

    @GetMapping("/login/oauth2/code/{provider}")
    public void callBack(HttpServletResponse response, @PathVariable String provider, @RequestParam("code") String code) throws IOException {
        if (provider.equals("kakao")) {
            String accessToken = kakaoOAuthService.login(code);
            response.sendRedirect("http://localhost:5173?accessToken=" + accessToken);
        }
    }
}
