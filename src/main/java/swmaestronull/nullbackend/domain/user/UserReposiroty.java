package swmaestronull.nullbackend.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserReposiroty extends JpaRepository<User, Long> {
}
