package code_sync_team.blog.image;

import code_sync_team.blog.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Image {
    @Id
    @GeneratedValue
    public Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    public User user;

    public String url;


}
