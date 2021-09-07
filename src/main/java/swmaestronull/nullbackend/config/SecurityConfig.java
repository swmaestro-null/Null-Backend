package swmaestronull.nullbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import swmaestronull.nullbackend.jwt.JwtAccessDeniedHandler;
import swmaestronull.nullbackend.jwt.JwtAuthenticationEntryPoint;
import swmaestronull.nullbackend.jwt.JwtSecurityConfig;
import swmaestronull.nullbackend.jwt.TokenProvider;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    public SecurityConfig(
            TokenProvider tokenProvider,
            JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
            JwtAccessDeniedHandler jwtAccessDeniedHandler) {
        this.tokenProvider = tokenProvider;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers(
                        "/h2-console/**",
                        "/favicon.ico"
                );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // 토큰을 사용하기 때문에 사용하지 않는다.

                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                .and()
                .headers()
                .frameOptions()
                .sameOrigin() // h2-console을 위한 설정

                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션을 사용하지 않는다.

                .and()
                .authorizeRequests() // HttpServletRequest를 사용하는 요청에 대한 접근제한 설정
                .antMatchers("/api/hello").permitAll() // 인증 없이 접근을 허용
                .antMatchers("/api/authenticate").permitAll() // 인증 없이 접근을 허용
                .antMatchers("/api/signup").permitAll() // 인증 없이 접근을 허용
                .anyRequest().authenticated() // 나머지 요청에 대해서는 인증을 받음

                .and()
                .apply(new JwtSecurityConfig(tokenProvider)); // JwtFilter를 적용한 JwtSecurityConfig 클래스 적용
    }
}
