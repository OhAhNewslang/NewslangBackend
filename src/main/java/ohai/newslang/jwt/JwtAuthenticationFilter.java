package ohai.newslang.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// /login 요청 > username, password 전송(post)
// UsernamePasswordAuthenticationFilter 동작
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    // /login 요청하면 로그인 시도를 위해서 실행되는 함수
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("JwtAuthenticationFilter: 로그인 시도중");

        // 1. username password 받음
        // 2. 정상인지 로그인 시도 > authenticationManager 로그인 시도를 하면
        // 3. PrincipalDetaisService 가 호출, loadUserByUsername 함수 실행

        // 4. PrincipalDetais 세션에 담고 (담는 이유 : 권한 관리를 위함)
        // 5. JWT 토큰 만들어서 응답

        return super.attemptAuthentication(request, response);
    }
}
