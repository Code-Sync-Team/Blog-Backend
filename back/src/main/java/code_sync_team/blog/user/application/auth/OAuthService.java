package code_sync_team.blog.user.application.auth;

import jakarta.servlet.http.HttpServletResponse;

public interface OAuthService {
    void redirect(HttpServletResponse response);

    // TODO : 반환타입 변경.
    String getUserEmail(String accessToken);



    String getAccessToken(String accessToken);
}
