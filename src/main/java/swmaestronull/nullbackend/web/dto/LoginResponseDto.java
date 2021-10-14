package swmaestronull.nullbackend.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import swmaestronull.nullbackend.domain.user.PaintUser;

@Getter
@NoArgsConstructor
public class LoginResponseDto extends BaseResponseDto {

    private LoginResponseData data;

    @Builder
    public LoginResponseDto(int code, String message, boolean success, String token, PaintUser paintUser) {
        super(code, message, success);
        this.data = LoginResponseData.builder()
                .email(paintUser.getEmail())
                .name(paintUser.getName())
                .phoneNumber(paintUser.getPhoneNumber())
                .token(token)
                .build();
    }

    @Data
    @Builder
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    static class LoginResponseData {
        private String email;
        private String name;
        private String phoneNumber;
        private String token;
    }
}
