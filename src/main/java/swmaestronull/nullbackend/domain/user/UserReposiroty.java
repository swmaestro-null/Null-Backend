package swmaestronull.nullbackend.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserReposiroty extends JpaRepository<User, Long> {

    Optional<User> findOneWithAuthoritiesByEmail(String email);
}
