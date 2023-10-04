package ohai.newslang.configuration.config;

import lombok.RequiredArgsConstructor;
import ohai.newslang.configuration.jwt.JwtTokenDecoder;
import ohai.newslang.configuration.jwt.JwtTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity  // 스프링 시큐리티 필터가 스프링 체인필터에 등록
public class SecurityConfiguration {
    private final JwtTokenDecoder td;
// 커스텀 CORS 설정
//    @Bean
//    public CorsConfigurationSource corsFilter(){
//
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(true); // 내 서버가 응답할 때 json 을 자바스크립트에서 처리할 수 있게 할지를 설정하는 것
//        config.addAllowedOrigin("*"); // 모든 ip 응답을 허용
//        config.addAllowedHeader("*"); // 모든 헤더의 응답을 허용
//        config.addAllowedMethod("*"); // 모든 post, get, put, delete, patch 요청을 허용
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/api/**", config);
//        return source;
//    }
// 로직을 타지않는 페이지들을 보안 로직에서 권한 검사하지 않게 하는 시큐리티 메서드
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // Spring Security를 적용하지 않을 리소스 설정
        return (web) -> web.ignoring().
                requestMatchers(new AntPathRequestMatcher("/h2-console/**"))
                .requestMatchers(new AntPathRequestMatcher("/api/member/in"))
                .requestMatchers(new AntPathRequestMatcher("/api/member/new"))
                .requestMatchers(new AntPathRequestMatcher("/api/member/id"))
                .requestMatchers(new AntPathRequestMatcher("/api/member/password","POST"))
                .requestMatchers(new AntPathRequestMatcher("/api/news/**"))
                .requestMatchers(new AntPathRequestMatcher("/api/media/**"))
                .requestMatchers(new AntPathRequestMatcher("/api/category/**"))
                .requestMatchers(new AntPathRequestMatcher("/api/keyword/**"));
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(IntrospectorUtil.getIntrospector(http)).servletPath("/path");

        http.csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .exceptionHandling(AbstractHttpConfigurer::disable)
                // Spring Security를 사용하는 경우, HTTP 기본 인증을 비활성화하고 다른 인증 메커니즘을 사용하기 위해서
                // http 기본 인증을 꺼놓음.
                .sessionManagement((sessionManagement) -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 세션을 유지하여 SessionId를 확인할 필요없이 요청 시에 토큰을 받아서 사용하면 되므로 세션을 유지할 이유가 없다.
                .authorizeHttpRequests(
                        (authorize) -> authorize
                                .requestMatchers(mvcMatcherBuilder.pattern("/api/member/**")).hasRole("USER")
                                .anyRequest().authenticated()   // 그 외 인증없이 접근 X
                        // whiteList 방식
                )
                // 커스텀 필터 추가 : 요청이 시작되기 전에 만들어놓은 JwtTokenFilter를 사용할 필터로 설정
                .addFilterBefore(new JwtTokenFilter(td), UsernamePasswordAuthenticationFilter.class);
        return http.build();    // 설정한 http를 생성
    }

}