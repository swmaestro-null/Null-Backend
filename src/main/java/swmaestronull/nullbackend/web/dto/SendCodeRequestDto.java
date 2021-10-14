package swmaestronull.nullbackend.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class SendCodeRequestDto {

    @NotNull
    @Size(min = 3, max = 50)
    private String email;

    @Builder
    public SendCodeRequestDto(String email) {
        this.email = email;
    }
}
