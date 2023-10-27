package ohai.newslang.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.dto.member.request.*;
import ohai.newslang.domain.dto.member.response.MemberInfoDto;
import ohai.newslang.domain.dto.member.response.TokenDto;
import ohai.newslang.domain.dto.request.RequestResult;
import ohai.newslang.service.memeber.MemberService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberApiController {

    private final MemberService memberService;
//    private final MailService mailService;

    @PostMapping("/new")
    public RequestResult join(@RequestBody @Valid JoinMemberDto joinMemberDto,
                              BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return RequestResult.builder()
            .resultCode("202")
            .resultMessage(bindingResult.getFieldError().getDefaultMessage())
            .build();
        }
        return memberService.createMember(joinMemberDto);
    }

    @PostMapping("/in")
    public TokenDto login(@RequestBody @Valid LoginMemberDto loginMemberDto,
                          BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return TokenDto.builder()
            .result(RequestResult.builder()
            .resultCode("202")
            .resultMessage(bindingResult.getFieldError().getDefaultMessage())
            .build()).build();
        }
        return memberService.logIn(loginMemberDto);
    }

    @GetMapping("")
    public MemberInfoDto memberInfo() {
        // 이미 로그인한 회원만 회원 정보 요청 api호출에 접근 할 수 있으므로
        // 예외처리 대상 제외
        return memberService.readMemberInfo();
    }

    @PutMapping("")
    public MemberInfoDto updateMemberInfo(@RequestBody @Valid UpdateMemberDto updateMemberDto,
                                          BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return MemberInfoDto.builder()
            .result(RequestResult.builder()
            .resultCode("202")
            .resultMessage(bindingResult.getFieldError().getDefaultMessage())
            .build()).build();
        }
        return memberService.updateMemberInfo(updateMemberDto);
    }

    @PatchMapping("/password")
    public MemberInfoDto updatePassword(@RequestBody @Valid UpdatePasswordDto updatePasswordDto,
                                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return MemberInfoDto.builder()
                    .result(RequestResult.builder()
                    .resultCode("202")
                    .resultMessage(bindingResult.getFieldError().getDefaultMessage())
                    .build()).build();
        }
        return memberService.updateMemberPassword(updatePasswordDto);
    }

    // 권한부여는 나중에
    @PatchMapping("/role")
    public String updateRole(@RequestParam("role") String role) {
        return role;
    }

    @DeleteMapping("")
    public RequestResult withdraw(@RequestBody WithdrawPasswordDto passwordDto,
                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return RequestResult.builder()
                    .resultCode("202")
                    .resultMessage(bindingResult.getFieldError().getDefaultMessage())
                    .build();
        }
        return memberService.deleteMember(passwordDto.getPassword());
    }

//    @PostMapping("/certify")
//    public RequestResult certifyToEmail(@RequestParam("email") String email) {
//        mailService.sendMail(email);
//        return RequestResult.builder()
//                .resultCode("200")
//                .resultMessage("인증번호가 발급되었습니다.").build();
//    }
//
//    @PostMapping("/id")
//    public FindIdDto certifyForId(@RequestBody CertifyDto certifyDto){
//        return mailService.checkNumberForId(certifyDto);
//    }
//
//    @PostMapping("/password")
//    public RequestResult certifyForPassword(@RequestBody CertifyDto certifyDto){
//        return mailService.checkNumberForPassword(certifyDto);
//    }
//
//    @PatchMapping("/newPassword")
//    public RequestResult newPassword(@RequestBody NewPasswordDto newPasswordDto){
//        return mailService.updatePassword(newPasswordDto);
//    }
}