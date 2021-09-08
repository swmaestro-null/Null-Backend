package swmaestronull.nullbackend.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TokenDto {

    private String token;

    @Builder
    public TokenDto(String token) {
        this.token = token;
    }
}
