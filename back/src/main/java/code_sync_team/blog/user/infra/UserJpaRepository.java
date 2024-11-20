package code_sync_team.blog.user.infra;


import code_sync_team.blog.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Long> {
}
