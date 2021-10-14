package swmaestronull.nullbackend.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swmaestronull.nullbackend.auth.TokenProvider;
import swmaestronull.nullbackend.domain.user.PaintUser;
import swmaestronull.nullbackend.domain.user.PaintUserRepository;
import swmaestronull.nullbackend.web.dto.SignupRequestDto;
import swmaestronull.nullbackend.web.exception.DuplicateMemberException;

import java.util.Collections;

@Service
public class UserService {
    private final PaintUserRepository paintUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public UserService(PaintUserRepository paintUserRepository, PasswordEncoder passwordEncoder, TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.paintUserRepository = paintUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    @Transactional
    public PaintUser signup(SignupRequestDto signupRequestDto) {
        if (paintUserRepository.findOneWithRoleByEmail(signupRequestDto.getEmail()).orElse(null) != null) {
            throw new DuplicateMemberException("이미 가입되어 있는 유저입니다.");
        }

        PaintUser paintUser = PaintUser.builder()
                .email(signupRequestDto.getEmail())
                .password(passwordEncoder.encode(signupRequestDto.getPassword()))
                .name(signupRequestDto.getName())
                .phoneNumber(signupRequestDto.getPhoneNumber())
                .roles(Collections.singletonList("ROLE_USER"))
                .activated(true)
                .build();

        return paintUserRepository.save(paintUser);
    }

    public String login(String email, String password) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(email, password);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return tokenProvider.createToken(authentication);
    }

    @Transactional
    public PaintUser findByEmail(String email) {
        PaintUser paintUser = paintUserRepository.findOneWithRoleByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("[email:" + email + "] 해당 유저가 없습니다."));
        return paintUser;
    }
}
