package swmaestronull.nullbackend.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

@Getter
@NoArgsConstructor
public class LoginResponseDto extends BaseResponseDto {

    private LoginResponseData data;

    @Builder
    public LoginResponseDto(int code, String message, boolean success, String token) {
        super(code, message, success);
        this.data = new LoginResponseData(token);
    }

    @Data
    @AllArgsConstructor
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    static class LoginResponseData {
        private String token;
    }
}
