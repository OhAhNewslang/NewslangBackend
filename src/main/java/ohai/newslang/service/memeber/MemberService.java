package ohai.newslang.service.memeber;

import ohai.newslang.domain.dto.member.JoinMemberDto;
import ohai.newslang.domain.dto.member.LoginMemberDto;
import ohai.newslang.domain.dto.member.RequestMemberInfoDto;
import ohai.newslang.domain.dto.member.ResponseMemberInfoDto;
import ohai.newslang.domain.entity.member.Member;

public interface MemberService {
    void createByUser(JoinMemberDto joinMemberDto);
    Boolean checkDuplicate(Member member);
    void logIn(LoginMemberDto loginMemberDto);
    ResponseMemberInfoDto readByUserInfo();
    ResponseMemberInfoDto updateByUserInfo(RequestMemberInfoDto modifyMemberDto);
    void deleteByUser(String password);
    Member loadMemberByMemberId(Long id);
}
