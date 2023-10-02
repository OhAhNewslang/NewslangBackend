package ohai.newslang.controller;

import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.dto.member.*;
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

    // 패스워드 변경, 이메일 변경, 권한 변경 추가 예정
    // DTO를 구성하려고 하니까 프론트쪽 설계가 좀 미흡한 것 같아서 일단 넘겼습니다.
    @PatchMapping("/password")
    public String updatePassword(@RequestParam("password") String password) {
        return password;
    }

    @PatchMapping("/email")
    public String updateEmail(@RequestParam("email") String email) {
        return email;
    }

    @PatchMapping("/role")
    public String updateRole(@RequestParam("role") String role) {
        return role;
    }

    @DeleteMapping("")
    public ResponseDto withdraw(@RequestParam("password") String password) {
        return memberService.deleteMember(password);
    }
}
