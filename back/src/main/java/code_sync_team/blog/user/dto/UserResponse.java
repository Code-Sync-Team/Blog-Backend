package code_sync_team.blog.user.dto;

import code_sync_team.blog.user.User;

public class UserResponse {
    public Long id;
    public String nickname;
    public String email;
    public String name;

    public UserResponse(User user) {
        this.id = user.getId();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.name = user.getName();
    }
}
