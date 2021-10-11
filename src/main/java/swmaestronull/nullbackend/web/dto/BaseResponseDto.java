package swmaestronull.nullbackend.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BaseResponseDto {
    private int code;
    private String message;
    private boolean success;

    public BaseResponseDto(int code, String message, boolean success) {
        this.code = code;
        this.message = message;
        this.success = success;
    }
}
