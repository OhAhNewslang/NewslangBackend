package ohai.newslang.service.memeber;

import ohai.newslang.domain.dto.member.JoinMemberDto;
import ohai.newslang.domain.dto.member.LoginMemberDto;
import ohai.newslang.domain.dto.member.MemberInfoDto;
import ohai.newslang.domain.entity.member.Member;

public interface MemberService {
    void createByUser(JoinMemberDto joinMemberDto);
    Boolean checkDuplicate(Member member);
    void logIn(LoginMemberDto loginMemberDto);
    MemberInfoDto readByUserInfo();
    public MemberInfoDto updateByUserInfo(MemberInfoDto memberInfoDto);
    void deleteByUser(String password);
    Member loadMemberByMemberId(Long id);
}
