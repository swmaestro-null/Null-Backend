package swmaestronull.nullbackend.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginResponseDto {

    private String token;

    @Builder
    public LoginResponseDto(String token) {
        this.token = token;
    }
}
