package code_sync_team.blog.user.application.auth.oauth;

import jakarta.servlet.http.HttpServletResponse;

public interface OAuthService {
    void redirect(HttpServletResponse response);
    String login(String code);
}
