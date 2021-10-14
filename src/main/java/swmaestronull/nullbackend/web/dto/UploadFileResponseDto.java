package swmaestronull.nullbackend.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

@Getter
@NoArgsConstructor
public class UploadFileResponseDto extends BaseResponseDto {

    private UploadFileResponseData data;

    @Builder
    public UploadFileResponseDto(int code, String message, boolean success, String url) {
        super(code, message, success);
        this.data = new UploadFileResponseData(url);
    }

    @Data
    @AllArgsConstructor
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    static class UploadFileResponseData {
        String url;
    }
}
