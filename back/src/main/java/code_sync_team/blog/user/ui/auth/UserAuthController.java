package code_sync_team.blog.user.ui.auth;

import code_sync_team.blog.user.domain.AuthType;
import code_sync_team.blog.user.ui.auth.support.AuthRedirector;
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
public class UserAuthController {

    private final AuthRedirector authRedirector;

    @GetMapping("/oauth/login/{provider}")
    public void oAuthLogin(@PathVariable String provider, HttpServletResponse response) {
        authRedirector.redirect(response, provider);
    }

    @GetMapping("/login/oauth2/code/{provider}")
    public void callBack(@PathVariable String provider, @RequestParam("code") String code) throws IOException {

    }
}
