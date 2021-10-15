package swmaestronull.nullbackend.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class CheckCodeRequestDto {

    @NotNull
    @Size(min = 3, max = 50)
    private String email;

    @NotNull
    private String code;

    @Builder
    public CheckCodeRequestDto(String email, String code) {
        this.email = email;
        this.code = code;
    }
}
