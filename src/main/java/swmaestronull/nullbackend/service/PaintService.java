package swmaestronull.nullbackend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import swmaestronull.nullbackend.web.dto.PaintResponseDto;
import swmaestronull.nullbackend.web.dto.ReferencePaintRequestDto;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@PropertySource("classpath:application-aws.properties")
public class PaintService {

    private final ObjectMapper objectMapper;

    @Value("${PaintApiUrl}")
    private String PaintApiUrl;

    public PaintService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public PaintResponseDto referencePaint(ReferencePaintRequestDto paintRequestDto) throws JsonProcessingException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        Map<String, Object> map = new HashMap<>();
        String baseAccessKey = "static/" + paintRequestDto.getEmail();
        map.put("referenceAccessKey", baseAccessKey + "/reference/" + paintRequestDto.getReferenceFileName());
        map.put("sketchAccessKey", baseAccessKey + "/sketch/" + paintRequestDto.getSketchFileName());
        map.put("resultAccessKey", baseAccessKey + "/result/" + "result" + LocalDateTime.now() + ".PNG");
        String params = objectMapper.writeValueAsString(map);

        HttpEntity entity = new HttpEntity(params, httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> responseEntity = restTemplate.exchange(
                PaintApiUrl + "/paint",
                HttpMethod.POST,
                entity,
                Map.class);
        String resultUrl = (String)responseEntity.getBody().get("resultUrl");
        return PaintResponseDto.builder()
                .code(0)
                .message("채색에 성공했습니다.")
                .success(true)
                .resultUrl(resultUrl)
                .build();
    }
}
