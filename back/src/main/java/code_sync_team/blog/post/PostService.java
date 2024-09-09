package code_sync_team.blog.post;

import code_sync_team.blog.post.dto.PostCreateRequest;
import code_sync_team.blog.user.User;
import code_sync_team.blog.user.UserRepository;
import code_sync_team.blog.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserService userService;

    public Post create(String title, String content, Long userId) {
        User user = userService.findById(userId);

        Post post = Post.builder()
                .title(title)
                .content(content)
                .author(user)
                .build();

        return postRepository.save(post);
    }

}
