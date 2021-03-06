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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import swmaestronull.nullbackend.auth.JwtAccessDeniedHandler;
import swmaestronull.nullbackend.auth.JwtAuthenticationEntryPoint;
import swmaestronull.nullbackend.auth.JwtSecurityConfig;
import swmaestronull.nullbackend.auth.TokenProvider;

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
    public void configure(WebSecurity web) {
        web
                .ignoring()
                .antMatchers(
                        "/h2-console/**",
                        "/favicon.ico",
                        "/swagger-ui.html",
                        "/webjars/**",
                        "/swagger-resources/**",
                        "/swagger/**",
                        "/configuration/security",
                        "/v2/api-docs"
                );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // ????????? ???????????? ????????? ???????????? ?????????.

                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                .and()
                .headers()
                .frameOptions()
                .sameOrigin() // h2-console??? ?????? ??????

                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // ????????? ???????????? ?????????.

                .and()
                .authorizeRequests() // HttpServletRequest??? ???????????? ????????? ?????? ???????????? ??????
                .antMatchers("/api/v1/user/signup").permitAll() // ?????? ?????? ????????? ??????
                .antMatchers("/api/v1/user/login").permitAll() // ?????? ?????? ????????? ??????
                .antMatchers("/api/v1/user/sendCode").permitAll() // ?????? ?????? ????????? ??????
                .antMatchers("/api/v1/user/checkCode").permitAll() // ?????? ?????? ????????? ??????
                .antMatchers("/test/**").permitAll() // ?????? ?????? ????????? ??????
                .anyRequest().authenticated() // ????????? ????????? ???????????? ????????? ??????

                .and()
                .apply(new JwtSecurityConfig(tokenProvider)); // JwtFilter??? ????????? JwtSecurityConfig ????????? ??????
    }

    @Bean
    public CorsConfigurationSource configurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
