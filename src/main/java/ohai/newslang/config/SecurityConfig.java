package ohai.newslang.config;

import lombok.RequiredArgsConstructor;
import ohai.newslang.filter.MyFilter1;
import ohai.newslang.filter.MyFilter3;
import ohai.newslang.jwt.JwtAuthenticationFilter;
import ohai.newslang.jwt.JwtAuthorizationFilter;
import ohai.newslang.repository.MemberRepository;
import ohai.newslang.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CorsConfig corsConfig;
    private final MemberRepository memberRepository;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.addFilterBefore(new MyFilter3(), SecurityContextPersistenceFilter.class);
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(corsConfig.corsFilter())
                .formLogin().disable() // 까지 거의 고정
                .httpBasic().disable() // 토큰 방식인 bearer 방식을 쓰겠음
                .addFilter(new JwtAuthenticationFilter(authenticationManager())) // AuthenticationManager
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), memberRepository))
                .authorizeRequests()
                .antMatchers("/api/v1/user/**")
                .hasAnyRole("ROLE_USER", "ROLE_MANAGER", "ROLE_ADMIN")
                .antMatchers("/api/v1/manager/**")
                .hasAnyRole("ROLE_MANAGER", "ROLE_ADMIN")
                .antMatchers("/api/v1/admin/**")
                .hasAnyRole("ROLE_ADMIN")
                .anyRequest().permitAll();
    }
}
