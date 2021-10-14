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
import swmaestronull.nullbackend.web.dto.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin(origins = "*")
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
    public SignupResponseDto signup(@Valid @RequestBody SignupRequestDto signupRequestDto) {
        PaintUser paintUser = userService.signup(signupRequestDto);
        SignupResponseDto signupResponseDto = SignupResponseDto.builder()
                .code(0)
                .message("signup success")
                .success(true)
                .entity(paintUser)
                .build();
        return signupResponseDto;
    }

    @ApiOperation(value = "로그인", notes = "email과 password로 로그인을 진행합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "로그인 성공")
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        String jwt = userService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());
        PaintUser paintUser = userService.findByEmail(loginRequestDto.getEmail());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        LoginResponseDto loginResponseDto = LoginResponseDto.builder()
                .code(0)
                .message("login success")
                .token(jwt)
                .paintUser(paintUser)
                .success(true)
                .build();
        return new ResponseEntity<>(loginResponseDto, httpHeaders, HttpStatus.OK);
    }
}
