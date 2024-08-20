package code_sync_team.blog.post.dto;

import lombok.Getter;

@Getter
public class PostCreateRequest {
    private String title;
    private String content;
}
