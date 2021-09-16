package swmaestronull.nullbackend.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import swmaestronull.nullbackend.service.S3Uploader;

import java.io.IOException;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController {

    private final S3Uploader s3Uploader;

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("hello");
    }

    @GetMapping("/upload")
    public String testPage(Model model) {
        return "main_page";
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam MultipartFile image) throws IOException {
        String url = s3Uploader.upload(image, "static");
        log.info("upload url=" + url);
        return "main_page";
    }
}
