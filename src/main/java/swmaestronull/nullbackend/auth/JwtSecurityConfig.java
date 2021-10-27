package swmaestronull.nullbackend.auth;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import swmaestronull.nullbackend.web.CorsFilter;

public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private TokenProvider tokenProvider;

    public JwtSecurityConfig(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // FilterChain에 등록
        JwtFilter customFilter = new JwtFilter(tokenProvider);
        CorsFilter corsFilter = new CorsFilter();
        http
                .addFilterBefore(corsFilter, ChannelProcessingFilter.class)
                .addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
