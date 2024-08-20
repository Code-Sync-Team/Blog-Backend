package code_sync_team.blog.post;


import code_sync_team.blog.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private String content;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User author;
}
