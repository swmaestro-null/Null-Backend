package swmaestronull.nullbackend.domain.emailcode;

import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
public class EmailCode {

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
}
