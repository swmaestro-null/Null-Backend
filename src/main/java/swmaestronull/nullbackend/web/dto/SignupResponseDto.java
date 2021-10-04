package swmaestronull.nullbackend.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import swmaestronull.nullbackend.domain.user.PaintUser;

@Getter
@NoArgsConstructor
public class SignupResponseDto {
    private String email;

    public SignupResponseDto(PaintUser entity) {
        this.email = entity.getEmail();
    }
}
