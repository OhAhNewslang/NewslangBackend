package ohai.newslang.service.memeber;

import lombok.RequiredArgsConstructor;
import ohai.newslang.configuration.jwt.TokenDecoder;
import ohai.newslang.domain.dto.member.request.JoinMemberDto;
import ohai.newslang.domain.dto.member.request.LoginMemberDto;
import ohai.newslang.domain.dto.member.request.UpdateMemberDto;
import ohai.newslang.domain.dto.member.request.UpdatePasswordDto;
import ohai.newslang.domain.dto.member.response.MemberInfoDto;
import ohai.newslang.domain.dto.member.response.TokenDto;
import ohai.newslang.domain.dto.request.RequestResult;
import ohai.newslang.domain.entity.member.Member;
import ohai.newslang.domain.entity.recommend.MemberRecommend;
import ohai.newslang.repository.member.MemberRepository;
import ohai.newslang.repository.recommand.MemberRecommendRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final MemberRecommendRepository memberRecommendRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenDecoder td;


    @Override
    @Transactional
    public RequestResult createMember(JoinMemberDto joinMemberDto) {
        Optional<Member> optionalMember = memberRepository.findByLoginId(joinMemberDto.getLoginId());
        //처음 가입하는 경우
        if(optionalMember.isEmpty()) {
            Member newMember = Member.createMember(MemberRecommend.createMemberRecommend(),
                    joinMemberDto.getName(),
                    joinMemberDto.getLoginId(),
                    joinMemberDto.getEmail(),
                    passwordEncoder.encode(joinMemberDto.getPassword()));
            memberRepository.save(newMember);
            return RequestResult.builder()
                    .resultCode("201")
                    .resultMessage("회원가입이 정상적으로 처리되었습니다.").build();
        }
        // 재가입 방지
        // 202 -> Request는 수신하였지만 요구사항을 수행 할 수 없음
        return RequestResult.builder()
                .resultCode("202")
                .resultMessage("이미 가입하신 회원 입니다.").build();
    }
    @Override
    public TokenDto logIn(LoginMemberDto loginMemberDto) {
        Optional<Member> optionalMember = memberRepository.findByLoginId(loginMemberDto.getLoginId());

        if(optionalMember.isPresent()) {
            Member findMember = optionalMember.get();
            if(passwordEncoder.matches(loginMemberDto.getPassword(), findMember.getPassword())){
                return TokenDto.builder()
                        .token(td.createToken(String.valueOf(findMember.getId()), String.valueOf(findMember.getRole())))
                        .result(RequestResult.builder().resultCode("200").resultMessage("로그인이 정상적으로 처리되었습니다.").build())
                        .build();
            } else {
                return TokenDto.builder()
                        .result(RequestResult.builder().resultCode("202").resultMessage("비밀번호가 틀렸습니다.").build())
                        .build();
            }
        } else {
            return TokenDto.builder()
                    .result(RequestResult.builder().resultCode("202").resultMessage("해당 아이디로 가입된 회원이 없습니다.").build())
                    .build();
        }
    }

    @Override
    public MemberInfoDto readMemberInfo() {
        Member currentMember = memberRepository.findByTokenId(td.currentUserId());
        return MemberInfoDto.builder()
                .email(currentMember.getEmail())
                .name(currentMember.getName())
                .imagePath(currentMember.getImagePath())
                .result(RequestResult.builder().resultCode("200").resultMessage("").build())
                .build();
    }

    @Override
    @Transactional
    public MemberInfoDto updateMemberInfo(UpdateMemberDto updateMemberDto) {
        Member currentMember = memberRepository.findByTokenId(td.currentUserId());
        RequestResult result = new RequestResult();

        // 수정하지 않고 수정 완료 버튼을 눌렀을때
        if (currentMember.getName().equals(updateMemberDto.getName())
                && currentMember.getImagePath().equals(updateMemberDto.getImagePath())
                && currentMember.getEmail().equals(updateMemberDto.getEmail())) {
            result = RequestResult.builder().resultCode("202").resultMessage("수정된 회원정보가 없습니다.").build();
        }

        // 이름이 수정 되었을 때
        if (!currentMember.getName().equals(updateMemberDto.getName())){
            currentMember.updateName(updateMemberDto.getName());
            result = RequestResult.builder().resultCode("200").resultMessage("회원 정보가 수정되었습니다.").build();
        }

        // 이미지가 수정 되었을 때
        if (!currentMember.getImagePath().equals(updateMemberDto.getImagePath())){
            currentMember.updateImagePath(updateMemberDto.getImagePath());
            result = RequestResult.builder().resultCode("200").resultMessage("회원 정보가 수정되었습니다.").build();
        }

        // 이메일이 수정 되었을 때
        if (!currentMember.getEmail().equals(updateMemberDto.getEmail())){
            currentMember.updateEmail(updateMemberDto.getEmail());
            result = RequestResult.builder().resultCode("200").resultMessage("회원 정보가 수정되었습니다.").build();
        }

        return MemberInfoDto.builder()
                .name(currentMember.getName())
                .email(currentMember.getEmail())
                .imagePath(currentMember.getImagePath())
                .result(result)
                .build();
    }

    @Override
    @Transactional
    public MemberInfoDto updateMemberPassword(UpdatePasswordDto updatePasswordDto) {
        Member currentMember = memberRepository.findByTokenId(td.currentUserId());
        if (passwordEncoder.matches(updatePasswordDto.getOldPassword(), currentMember.getPassword())) {
            currentMember.updatePassword(passwordEncoder.encode(updatePasswordDto.getNewPassword()));
            return MemberInfoDto.builder()
                    .name(currentMember.getName())
                    .email(currentMember.getEmail())
                    .imagePath(currentMember.getImagePath())
                    .result(RequestResult.builder().resultCode("200").resultMessage("비밀번호 변경 완료.").build())
                    .build();
        } else {
            return MemberInfoDto.builder()
                    .name(currentMember.getName())
                    .email(currentMember.getEmail())
                    .imagePath(currentMember.getImagePath())
                    .result(RequestResult.builder().resultCode("202").resultMessage("현재 비밀번호가 일치 하지 않습니다.").build())
                    .build();
        }
    }

    @Override
    @Transactional
    public RequestResult deleteMember(String password) {
        Member currentMember = memberRepository.findByTokenId(td.currentUserId());
        if (passwordEncoder.matches(password, currentMember.getPassword())) {
            memberRepository.delete(currentMember);
            return RequestResult.builder().resultCode("200").resultMessage("탈퇴가 정상적으로 처리되었습니다.").build();

        } else {
            return RequestResult.builder().resultCode("202").resultMessage("이미 탈퇴된 회원 입니다.").build();
        }
    }

    @Override
    public Long getMemberId(String loginId) {
        return memberRepository.findByLoginId(loginId).get().getId();
    }
}
