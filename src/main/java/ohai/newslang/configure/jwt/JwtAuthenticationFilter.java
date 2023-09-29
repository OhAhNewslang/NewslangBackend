package ohai.newslang.configure.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import ohai.newslang.configure.auth.PrincipalDetails;
import ohai.newslang.domain.entity.member.Member;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

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
        try {
            ObjectMapper om = new ObjectMapper();
            Member member = om.readValue(request.getInputStream(), Member.class);

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(member.getName(), member.getPassword());

            // PrincipalDetailsService 의 LoadUserByUsername() 실행 후
            // 정상이면 authentication 리턴 > 로그인 정상 (username 과 password 일치)
            Authentication authentication =
                    authenticationManager.authenticate(authenticationToken);

            // authentication 객체가 session영역에 저장됨 -> 로그인이 되었다는 뜻
            PrincipalDetails principalDetails = (PrincipalDetails)authentication.getPrincipal();
            System.out.println("로그인 완료 : " + principalDetails.getMember().getName());

            // authentication 객체를 session영역에 저장하기 위해 리턴
            // 리턴의 이유는 권한 관리를 security가 대신 해줌
            // JWT 토큰 사용하며 세션 만들 필요 없지만, 권한 처리 때문에 session 넣어주는 것임
            return authentication;
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 2. 정상인지 로그인 시도 > authenticationManager 로그인 시도를 하면
        // 3. PrincipalDetaisService 가 호출, loadUserByUsername 함수 실행

        // 4. PrincipalDetais 세션에 담고 (담는 이유 : 권한 관리를 위함)
        // 5. JWT 토큰 만들어서 응답

        return null;
    }

    // attemptAuthentication 실행 후 인증이 정상적이면 실행됨
    // JWT 토큰 만들어서 request 요청한 사용자에게 JWT 토큰을 응답
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        System.out.println("successfulAuthentication 실행됨, 인증 완료되었음");
        PrincipalDetails principalDetails = (PrincipalDetails)authResult.getPrincipal();
        String jwtToken = JWT.create()
                .withSubject(principalDetails.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+JwtProperties.EXPIRATION_TIME)) // 만료 시간
                .withClaim("id", principalDetails.getMember().getId()) // 비공개 클레임, 아무거나 넣어도됨
                .withClaim("name", principalDetails.getMember().getName())// 비공개 클레임, 아무거나 넣어도됨
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));
        response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + jwtToken);
    }
}
