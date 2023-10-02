package ohai.newslang.service.memeber;

import lombok.RequiredArgsConstructor;
import ohai.newslang.configuration.jwt.TokenDecoder;
import ohai.newslang.domain.dto.member.*;
import ohai.newslang.domain.dto.request.RequestResult;
import ohai.newslang.domain.dto.request.ResponseDto;
import ohai.newslang.domain.entity.member.Member;
import ohai.newslang.repository.member.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenDecoder td;


    @Override
    @Transactional
    public ResponseDto createMember(JoinMemberDto joinMemberDto) {
        Optional<Member> optionalMember = memberRepository.findByEmail(joinMemberDto.getEmail());
        //처음 가입하는 경우
        if(optionalMember.isEmpty()) {
            Member newUser = Member.builder()
                    .name(joinMemberDto.getName())
                    .email(joinMemberDto.getEmail())
                    .password(passwordEncoder.encode(joinMemberDto.getPassword()))
                    .build();
            //HOST가 가입하는 경우
//            if(joinMemberDto.getRole() == UserRole.ROLE_HOST) {
//                Hotel hotel = hotelRepository.findByHotelName(joinMemberDto.getName());
//                newUser.foreignHotel(hotel);
//            }
             memberRepository.save(newUser);
            // 201 -> 정상적으로 리소스(회원)가 생성됨.
             return ResponseDto.builder()
                     .result(RequestResult.builder().isSuccess(true).failCode("201").build())
                     .resultMessage("회원가입이 정상적으로 처리되었습니다.").build();
        } else {
            // 재가입 방지
            // 202 -> Request는 수신하였지만 요구사항을 수행 할 수 없음
            return ResponseDto.builder().result(RequestResult.builder().isSuccess(false).failCode("202").build())
                    .resultMessage("이미 가입하신 이메일 입니다.").build();
        }
    }
    @Override
    public TokenDto logIn(LoginMemberDto loginMemberDto) {
        Optional<Member> optionalMember = memberRepository.findByEmail(loginMemberDto.getEmail());

        if(optionalMember.isPresent()) {
            Member findMember = optionalMember.get();
            if(passwordEncoder.matches(loginMemberDto.getPassword(), findMember.getPassword())){
                return TokenDto.builder()
                        .token(td.createToken(String.valueOf(findMember.getId()), String.valueOf(findMember.getRole())))
                        .responseDto(ResponseDto.builder()
                                .result(RequestResult.builder().isSuccess(true).failCode("").build())
                                .resultMessage("로그인이 정상적으로 처리되었습니다.").build())
                        .build();
            } else {
                return TokenDto.builder()
                        .responseDto(ResponseDto.builder()
                                .result(RequestResult.builder().isSuccess(true).failCode("201").build())
                                .resultMessage("비밀번호가 틀렸습니다.").build())
                        .build();
            }
        } else {
            return TokenDto.builder()
                    .responseDto(ResponseDto.builder()
                            .result(RequestResult.builder().isSuccess(true).failCode("201").build())
                            .resultMessage("해당 이메일로 가입된 회원이 없습니다.").build())
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
                .responseDto(ResponseDto.builder().result(RequestResult.builder().isSuccess(true).failCode("200").build()).build()
                ).build();
    }

    @Override
    @Transactional
    public MemberInfoDto updateMemberInfo(UpdateMemberDto updateMemberDto) {
        Member currentMember = memberRepository.findByTokenId(td.currentUserId());
        ResponseDto result = new ResponseDto();

        if (currentMember.getName().equals(updateMemberDto.getName())
                && currentMember.getImagePath().equals(updateMemberDto.getImagePath())) {
            result = ResponseDto.builder()
                    .result(RequestResult.builder().isSuccess(false).failCode("201").build())
                    .resultMessage("수정된 회원정보가 없습니다.").build();
        }
        if (!currentMember.getName().equals(updateMemberDto.getName())){
            currentMember.updateName(updateMemberDto.getName());
            result = ResponseDto.builder()
                    .result(RequestResult.builder().isSuccess(true).failCode("").build())
                    .resultMessage("회원 정보가 수정되었습니다.").build();
        }

        if (!currentMember.getImagePath().equals(updateMemberDto.getImagePath())){
            currentMember.updateImagePath(updateMemberDto.getImagePath());
            result = ResponseDto.builder()
                    .result(RequestResult.builder().isSuccess(true).failCode("").build())
                    .resultMessage("회원 정보가 수정되었습니다.").build();
        }


        return MemberInfoDto.builder()
                .name(currentMember.getName())
                .email(currentMember.getEmail())
                .imagePath(currentMember.getImagePath())
                .responseDto(result)
                .build();
    }

    @Override
    @Transactional
    public ResponseDto deleteMember(String password) {
        Member findMember = memberRepository.findByTokenId(td.currentUserId());
        System.out.println("@@@@@@@@@@@@@@@@@@@@@RequestBody = ");
        System.out.println("password = " + password);
        if (passwordEncoder.matches(password, findMember.getPassword())) {
            memberRepository.delete(findMember);
            return ResponseDto.builder()
                    .result(RequestResult.builder().isSuccess(true).failCode("").build())
                    .resultMessage("로그인이 정상적으로 처리되었습니다.").build();

        } else {
            return ResponseDto.builder()
                    .result(RequestResult.builder().isSuccess(false).failCode("201").build())
                    .resultMessage("이미 삭제된 회원 입니다.").build();
        }
    }
}
