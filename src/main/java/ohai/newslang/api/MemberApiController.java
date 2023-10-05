package ohai.newslang.api;

import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.dto.member.request.*;
import ohai.newslang.domain.dto.member.response.FindIdDto;
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

    @DeleteMapping("")
    public RequestResult withdraw(@RequestParam("password") String password) {
        return memberService.deleteMember(password);
    }

    @PostMapping("/certify")
    public RequestResult certifyToEmail(@RequestParam("email") String email) {
        mailService.sendMail(email);
        return RequestResult.builder()
                .resultCode("200")
                .resultMessage("인증번호가 발급되었습니다.").build();
    }

    @PostMapping("/id")
    public FindIdDto certifyForId(@RequestBody CertifyDto certifyDto){
        return mailService.checkCodeForId(certifyDto);
    }

    @PostMapping("/password")
    public RequestResult certifyForPassword(@RequestBody CertifyDto certifyDto){
        return mailService.checkCodeForPassword(certifyDto);
    }

    @PatchMapping("/newPassword")
    public RequestResult newPassword(@RequestBody NewPasswordDto newPasswordDto){
        return mailService.updatePassword(newPasswordDto);
    }
}
