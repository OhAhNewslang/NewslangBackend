package ohai.newslang.controller;

import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.dto.member.request.JoinMemberDto;
import ohai.newslang.domain.dto.member.request.LoginMemberDto;
import ohai.newslang.domain.dto.member.request.UpdateMemberDto;
import ohai.newslang.domain.dto.member.request.UpdatePasswordDto;
import ohai.newslang.domain.dto.member.response.MemberInfoDto;
import ohai.newslang.domain.dto.member.response.TokenDto;
import ohai.newslang.domain.dto.request.ResponseDto;
import ohai.newslang.service.memeber.MemberService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class MemberApiController {

    private final MemberService memberService;
    @PostMapping("/new")
    public ResponseDto join(@RequestBody JoinMemberDto joinMemberDto) {
        return memberService.createMember(joinMemberDto);
    }

    @PostMapping("/in")
    public TokenDto login(@RequestBody LoginMemberDto loginMemberDto) {
        return memberService.logIn(loginMemberDto);
    }

    @GetMapping("")
    public MemberInfoDto memberInfo() {
        return memberService.readMemberInfo();
    }

    @PutMapping("")
    public MemberInfoDto updateMemberInfo(@RequestBody UpdateMemberDto updateMemberDto) {
        return memberService.updateMemberInfo(updateMemberDto);
    }

    @PatchMapping("/password")
    public MemberInfoDto updatePassword(@RequestBody UpdatePasswordDto updatePasswordDto) {
        return memberService.updateMemberPassword(updatePasswordDto);
    }

    // 권한부여는 나중에
    @PatchMapping("/role")
    public String updateRole(@RequestParam("role") String role) {
        return role;
    }

    @DeleteMapping("")
    public ResponseDto withdraw(@RequestParam("password") String password) {
        return memberService.deleteMember(password);
    }
}
