package code_sync_team.blog.global.auth;

import code_sync_team.blog.user.User;

public class UserContextHolder {
    private static final ThreadLocal<UserDetail> context = new ThreadLocal<>();

    public static void setContext(UserDetail user) {
        context.set(user);
    }

    public static void clearContext() {
        context.remove();
    }

    public static UserDetail getContext() {
        return context.get();
    }
}
