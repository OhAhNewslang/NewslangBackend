package ohai.newslang.configuration.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohai.newslang.repository.member.MemberRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenDecoder implements TokenDecoder{

    private final MemberRepository memberRepository;

    // secretKey값을 디코딩한 결과를 저장하는 Key
    private Key key;
    // JWT payload에 들어갈 유효기간 (계산용)
    private final long tokenValidMillisecond = 1000L * 60 * 60;

    // 빈이 주입받은 직후 자동 실행되는 메소드, secretKey 값을 Base64로 디코딩
    @Override
    @PostConstruct
    public void init() {
        // HMAC-SHA-256 알고리즘에 적합한 256비트 이상의 키 생성
        key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    @Override
    public String createToken(String id, String role) {
        Claims claims = Jwts.claims();

        // 토큰이 될 claims의 Subject 영역에 memberId 등록
        claims.setSubject(id);
        // 토큰이 될 claims의 Audience 영역에 memberRole 등록
        claims.setAudience(role);
        // 유효기간 계산할 현재 시간 저장
        Date now = new Date();

        // 생성된 토큰값 세션에 저장
//        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
//        req.getSession().setAttribute("Token", token);

        // 헤더에 전송
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add("Authorization", token);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)   // 토큰 생성 시간
                .setExpiration(new Date(now.getTime() + tokenValidMillisecond)) // 토큰 만료 기간
                .signWith(key) // 암호화 알고리즘과 SecretKey 세팅
                .compact(); // 패키징

    }

    // 클레임을 토큰으로 만들고 이를 이용해 authentication 객체 만들어서 리턴
    @Override
    public Authentication getAuthentication(String token) {
        // 토큰에서 추출한 userId를 기반으로 user객체 생성
        // 토큰에서 추출한 Role을 기반으로 시큐리티 메소드에 들어갈 Authority Collection 생성
        // List형태로 생성하는 이유는, 한 User마다 여러 역할을 가질 수 있기 때문이다
        // User A가 게시판1에서는 관리자이지만 게시판2에서는 그냥 user일 수 있음.
        Long principal = tokenToId(token);
        // 다중 ROLE 방식
//        Collection<? extends GrantedAuthority> authorities =
//                Arrays.stream(principal.getRoles().split(",")).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        // 단일 ROLE 방식
        Set<GrantedAuthority> setAuths = new HashSet<>();
        setAuths.add(new SimpleGrantedAuthority(String.valueOf(memberRepository.findByTokenId(principal).getRole())));

        // Filter에서는 토큰의 존재 여부 + 만료되지 않음 이라는 정보만 있으면 검증이 완료된 상태이다.
        // Security에서 우리의 필터를 통해 권한 필터링을 하기 위해 Authentication 객체를 생성해서
        // SecurityContextHolder에 저장 시켜놓는다.
        // 흐름상으로 설명하면 그 다음 차례이지만 메서드 한,두개 가 동작하는 시간은 정말 짧기 때문에
        // 거의 동시에 진행된다고 생각해도 무방할 정도로 바로
        // SecurityContextHolder의 권한 정보를 이용해 Security에서 필터링한다.
        // Client
        // -> Filter(SecurityContextHolder)
        // -> Security(SecurityContextHolder)
        // -> ApiController
        // -> Ours Logic
        // 요약
        // 1. Filter에서 토큰이 살아있는지 검사
        // 2. 살아있다면 토큰 정보(ID,ROLE)를 UsernamePassword~~ 형태로 SecurityContextHolder 등록
        // 3. Security가 ContextHolder의 정보를 이용해 알아서 필터링
        // 4. 우리가 작성한 로직 호출 및 작동, 리턴 값 리턴
        return new UsernamePasswordAuthenticationToken(principal, "", setAuths);
    }

    @Override
    public Long tokenToId(String token) {
        // 토큰을 들고와서 id로 반환해주는 메소드
        String info = Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        return Long.parseLong(info);
    }

    @Override
    public String tokenToRole(String token) {
        // 토큰을 들고와서 role을 반환해주는 메소드
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token)
                .getBody()
                .getAudience();
    }

    @Override
    public String resolveToken(HttpServletRequest req) {
//        return String.valueOf(req.getSession().getAttribute("Token"));
        // 헤더에 있는 토큰값 추출해서 사용
        return req.getHeader("X-AUTH-TOKEN");
    }

    @Override
    public boolean expiredToken(String token){
        // 토큰 유효성 검증 수행
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("로그인 먼저 해주세요.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

    @Override
    public Long currentMemberId() {
        // 현재 유저 정보는 SecurityContextHolder에
        // Authentication객체에 (memberId, 유효한지, 역할)로 저장해놓은 상태이므로
        // Authentication 가져오기
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Security Context에 인증 정보가 없는 상태
        if(authentication == null){
            throw new NoSuchElementException("로그인 먼저 시도해주세요.");
        }

        // principal객체로 저장된 user를 받기 위한 코드
        if (authentication.getPrincipal() instanceof Long) {
            return (Long) authentication.getPrincipal();
        } else {
            throw new NullPointerException("로그인 먼저 시도해주세요.");
        }
    }
}