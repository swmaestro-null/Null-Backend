package swmaestronull.nullbackend.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponseDto<T> {
    private int code;
    private T data;
    private String message;
    private boolean success;

    public ResponseDto(int code, T data, String message, boolean success) {
        this.code = code;
        this.data = data;
        this.message = message;
        this.success = success;
    }
}
