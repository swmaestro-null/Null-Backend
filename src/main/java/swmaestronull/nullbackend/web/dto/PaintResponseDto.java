package swmaestronull.nullbackend.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PaintResponseDto extends BaseResponseDto {

    private PaintResponseData data;

    @Builder
    public PaintResponseDto(int code, String message, boolean success, String resultUrl) {
        super(code, message, success);
        this.data = PaintResponseData.builder()
                .resultUrl(resultUrl)
                .build();
    }

    @Data
    @Builder
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    static class PaintResponseData {
        private String resultUrl;
    }

}
