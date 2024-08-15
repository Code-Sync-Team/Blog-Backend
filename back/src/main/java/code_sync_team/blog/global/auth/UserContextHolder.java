package code_sync_team.blog.global.auth;

import code_sync_team.blog.user.User;

public class UserContextHolder {
    private static final ThreadLocal<User> context = new ThreadLocal<>();

    public static void setContext(User user) {
        context.set(user);
    }

    public static void clearContext() {
        context.remove();
    }

    public static User getContext() {
        return context.get();
    }
}
