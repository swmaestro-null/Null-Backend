package swmaestronull.nullbackend.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class HelloController {
    @GetMapping("/test")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("hello");
    }
}
