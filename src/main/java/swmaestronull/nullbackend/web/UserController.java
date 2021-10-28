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
import swmaestronull.nullbackend.service.EmailService;
import swmaestronull.nullbackend.service.UserService;
import swmaestronull.nullbackend.web.dto.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;
    private final EmailService emailService;

    public UserController(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    @ApiOperation(value = "회원가입", notes = "회원가입을 진행합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "회원가입 성공")
    })
    @PostMapping("/signup")
    public SignupResponseDto signup(@Valid @RequestBody SignupRequestDto signupRequestDto) {
        return userService.signup(signupRequestDto);
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
                .message("로그인에 성공했습니다.")
                .token(jwt)
                .paintUser(paintUser)
                .success(true)
                .build();
        return new ResponseEntity<>(loginResponseDto, httpHeaders, HttpStatus.OK);
    }

    @ApiOperation(value = "회원수정", notes = "회원정보를 수정합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "회원수정 성공")
    })
    @PostMapping("/update")
    public BaseResponseDto update(@Valid @RequestBody UserUpdateRequestDto userUpdateRequestDto) {
        return userService.update(userUpdateRequestDto);
    }

    @ApiOperation(value = "이메일 인증", notes = "이메일에 인증 코드를 전송합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "인증 코드 전송 성공")
    })
    @PostMapping("/sendCode")
    public BaseResponseDto sendCode(@RequestBody SendCodeRequestDto sendCodeRequestDto) throws UnsupportedEncodingException, MessagingException {
        BaseResponseDto responseDto = emailService.sendMessage(sendCodeRequestDto.getEmail());
        return responseDto;
    }

    @ApiOperation(value = "이메일 인증 코드 확인", notes = "이메일 인증 코드를 확인합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "인증 코드 확인 성공")
    })
    @PostMapping("/checkCode")
    public BaseResponseDto checkCode(@RequestBody CheckCodeRequestDto checkCodeRequestDto){
        BaseResponseDto responseDto = emailService.checkCode(checkCodeRequestDto.getEmail(), checkCodeRequestDto.getCode());
        return responseDto;
    }
}
