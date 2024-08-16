package code_sync_team.blog.global.interceptor;

import code_sync_team.blog.global.auth.JwtProvider;
import code_sync_team.blog.global.auth.LoginUser;
import code_sync_team.blog.global.auth.UserContextHolder;
import code_sync_team.blog.global.auth.exception.AuthErrorCode;
import code_sync_team.blog.global.auth.exception.CustomAuthException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthInterceptor implements HandlerInterceptor {
    private final JwtProvider jwtProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (!isAuthMethod(handler)) {
            return true;
        }

        String bearerToken = getBearerToken(request);

        if (bearerToken == null) throw new CustomAuthException(AuthErrorCode.LOGIN_IS_REQUIRED);

        if (jwtProvider.verifyToken(bearerToken)) UserContextHolder.setContext(jwtProvider.parseToken(bearerToken));

        else {
            throw new CustomAuthException(AuthErrorCode.TOKEN_EXPIRED);
        }

        return true;
    }

    private String getBearerToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }

    private boolean isAuthMethod(final Object handler) {
        if (handler instanceof ResourceHttpRequestHandler) {
            return false;
        }

        final HandlerMethod handlerMethod = (HandlerMethod) handler;
        if (handlerMethod.hasMethodAnnotation(LoginUser.class)) {
            return true;
        }

        return false;
    }
}
