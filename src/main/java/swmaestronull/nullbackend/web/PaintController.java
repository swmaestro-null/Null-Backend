package swmaestronull.nullbackend.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import swmaestronull.nullbackend.service.PaintService;
import swmaestronull.nullbackend.service.S3Uploader;
import swmaestronull.nullbackend.web.dto.ReferencePaintRequestDto;
import swmaestronull.nullbackend.web.dto.UploadFileResponseDto;
import swmaestronull.nullbackend.web.dto.PaintResponseDto;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api/v1/paint")
@CrossOrigin(origins = "*")
public class PaintController {

    private final S3Uploader s3Uploader;
    private final PaintService paintService;

    public PaintController(S3Uploader s3Uploader, PaintService paintService) {
        this.s3Uploader = s3Uploader;
        this.paintService = paintService;
    }

    @ApiOperation(value = "이미지 업로드", notes = "채색할 이미지를 업로드 합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "업로드 성공")
    })
    @PostMapping("/upload/{imageType}/{email}")
    public UploadFileResponseDto uploadFile(
            @PathVariable String imageType,
            @PathVariable String email,
            @RequestParam MultipartFile image) throws IOException {
        String dirName = "static" + "/" + email + "/" + imageType;
        String url = s3Uploader.upload(image, dirName);
        log.info("upload url=" + url);
        return UploadFileResponseDto.builder()
                .code(0)
                .url(url)
                .message("이미지 업로드에 성공했습니다.")
                .success(true)
                .build();
    }

    @ApiOperation(value = "이미지 채색", notes = "이미지를 채색합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "채색 성공")
    })
    @PostMapping("/reference")
    public PaintResponseDto referencePaint(@RequestBody ReferencePaintRequestDto paintRequestDto) throws JsonProcessingException {
        return paintService.referencePaint(paintRequestDto);
    }
}
