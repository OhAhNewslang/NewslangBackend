package ohai.newslang.service.memeber;

import ohai.newslang.domain.dto.member.*;
import ohai.newslang.domain.dto.request.ResponseDto;
import ohai.newslang.domain.entity.member.Member;

public interface MemberService {
    ResponseDto createMember(JoinMemberDto joinMemberDto);
    TokenDto logIn(LoginMemberDto loginMemberDto);
    MemberInfoDto readMemberInfo();
    MemberInfoDto updateMemberInfo(UpdateMemberDto memberInfoDto);
    ResponseDto deleteMember(String password);
}
