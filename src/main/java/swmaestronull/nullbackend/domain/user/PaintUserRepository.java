package swmaestronull.nullbackend.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaintUserRepository extends JpaRepository<PaintUser, Long> {

    Optional<PaintUser> findOneWithRoleByEmail(String email);
}
