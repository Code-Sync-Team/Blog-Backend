package code_sync_team.blog.global.auth;

import code_sync_team.blog.user.User;
import lombok.Getter;

@Getter
public class UserDetail {
    private Long id;
    private String role;

    public UserDetail(User user) {
        this.id = user.getId();
        this.role = "ROLE_USER";
    }
}
