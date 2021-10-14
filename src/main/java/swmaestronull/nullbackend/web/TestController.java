package swmaestronull.nullbackend.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import swmaestronull.nullbackend.service.S3Uploader;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController {

    @Autowired
    private final S3Uploader s3Uploader;
    private final ObjectMapper objectMapper;

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
        // paintRequest();
        return "main_page";
    }

    /*
    private void paintRequest(String uploadUrl, String resultUrl) throws JsonProcessingException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        Map<String, Object> map = new HashMap<>();
        map.put("uploadAccessKey", uploadUrl);
        map.put("resultAccessKey", resultUrl);
        String params = objectMapper.writeValueAsString(map);

        HttpEntity entity = new HttpEntity(params, httpHeaders);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "http://localhost:8000/paint",
                HttpMethod.POST,
                entity,
                String.class);

        System.out.println(responseEntity.getStatusCode());
        System.out.println(responseEntity.getBody());
    }
     */
}
