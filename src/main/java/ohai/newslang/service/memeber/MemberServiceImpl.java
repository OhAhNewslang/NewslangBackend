package ohai.newslang.service.memeber;

import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.dto.member.JoinMemberDto;
import ohai.newslang.domain.dto.member.LoginMemberDto;
import ohai.newslang.domain.dto.member.RequestMemberInfoDto;
import ohai.newslang.domain.dto.member.ResponseMemberInfoDto;
import ohai.newslang.domain.entity.member.Member;
import ohai.newslang.repository.member.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
    private MemberRepository memberRepository;

    @Override
    public void createByUser(JoinMemberDto joinMemberDto) {

    }

    @Override
    public Boolean checkDuplicate(Member member) {
        return null;
    }

    @Override
    public void logIn(LoginMemberDto loginMemberDto) {

    }

    @Override
    public ResponseMemberInfoDto readByUserInfo() {
        return null;
    }

    @Override
    public ResponseMemberInfoDto updateByUserInfo(RequestMemberInfoDto modifyMemberDto) {
        return null;
    }

    @Override
    public void deleteByUser(String password) {

    }

    @Override
    public Member loadMemberByMemberId(Long id) {
        return null;
    }
}
