package swmaestronull.nullbackend.domain.emailcode;

import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@NoArgsConstructor
public class EmailCode {

    private static final int validMinutes = 3;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String code;

    @Column
    boolean isChecked;

    @Column
    LocalDateTime createdCodeTime;

    @Builder
    public EmailCode(String email, String code) {
        this.email = email;
        this.code = code;
        this.isChecked = false;
        this.createdCodeTime = LocalDateTime.now();
    }

    public void updateCode(String code) {
        this.code = code;
        this.createdCodeTime = LocalDateTime.now();
    }

    public boolean checkCode(String code) {
        return this.code.equals(code);
    }

    public boolean isValid() {
        long diff = ChronoUnit.MINUTES.between(this.createdCodeTime, LocalDateTime.now());
        return diff < validMinutes;
    }
}
