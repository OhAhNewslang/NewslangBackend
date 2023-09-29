package ohai.newslang.configure.auth;

import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.entity.member.Member;
import ohai.newslang.repository.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// http://localhost:8080/login
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        System.out.println("PrincipalDetailsService");
        Member member = memberRepository.findByUserName(userName);
        return new PrincipalDetails(member);
    }
}
