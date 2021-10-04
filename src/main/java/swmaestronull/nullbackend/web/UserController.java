package swmaestronull.nullbackend.web;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import swmaestronull.nullbackend.auth.JwtFilter;
import swmaestronull.nullbackend.domain.user.PaintUser;
import swmaestronull.nullbackend.service.UserService;
import swmaestronull.nullbackend.web.dto.LoginRequestDto;
import swmaestronull.nullbackend.web.dto.TokenDto;
import swmaestronull.nullbackend.web.dto.SignupRequestDto;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "회원가입", notes = "회원가입을 진행합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "회원가입 성공")
    })
    @PostMapping("/signup")
    public ResponseEntity<PaintUser> signup(@Valid @RequestBody SignupRequestDto signupRequestDto) {
        return ResponseEntity.ok(userService.signup(signupRequestDto));
    }

    @ApiOperation(value = "로그인", notes = "email과 password로 로그인을 진행합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "로그인 성공")
    })
    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        String jwt = userService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        return new ResponseEntity<>(new TokenDto(jwt), httpHeaders, HttpStatus.OK);
    }
}
