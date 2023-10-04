package ohai.newslang.api;

import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.dto.member.request.JoinMemberDto;
import ohai.newslang.domain.dto.member.request.LoginMemberDto;
import ohai.newslang.domain.dto.member.request.UpdateMemberDto;
import ohai.newslang.domain.dto.member.request.UpdatePasswordDto;
import ohai.newslang.domain.dto.member.response.MemberInfoDto;
import ohai.newslang.domain.dto.member.response.TokenDto;
import ohai.newslang.domain.dto.request.RequestResult;
import ohai.newslang.service.memeber.MailService;
import ohai.newslang.service.memeber.MemberService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberApiController {

    private final MemberService memberService;
    private final MailService mailService;
    @PostMapping("/new")
    public RequestResult join(@RequestBody JoinMemberDto joinMemberDto) {
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

    @PostMapping("/id")
    public String findId(@RequestParam("mail") String mail) {
        return mailService.sendMail(mail);
    }

    @PostMapping("/password")
    public String findPassword(@RequestParam("mail") String mail) {
        return mailService.sendMail(mail);
    }

    @DeleteMapping("")
    public RequestResult withdraw(@RequestParam("password") String password) {
        return memberService.deleteMember(password);
    }
}
