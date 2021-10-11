package swmaestronull.nullbackend.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import swmaestronull.nullbackend.domain.user.PaintUser;

@Getter
@NoArgsConstructor
public class SignupResponseDto extends BaseResponseDto {

    private SignupResponseData data;

    @Builder
    public SignupResponseDto(int code, String message, boolean success, PaintUser entity) {
        super(code, message, success);
        this.data = new SignupResponseData(entity.getEmail());
    }

    @Data
    @AllArgsConstructor
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    static class SignupResponseData {
        private String email;
    }
}
