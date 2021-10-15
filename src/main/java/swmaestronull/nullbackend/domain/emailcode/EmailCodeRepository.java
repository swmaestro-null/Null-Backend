package swmaestronull.nullbackend.domain.emailcode;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailCodeRepository extends JpaRepository<EmailCode, Long> {

    Optional<EmailCode> findByEmail(String email);
}
