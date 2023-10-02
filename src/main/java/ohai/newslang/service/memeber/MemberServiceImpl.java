//package ohai.newslang.service.memeber;
//
//import lombok.RequiredArgsConstructor;
//import ohai.newslang.configuration.jwt.TokenDecoder;
//import ohai.newslang.domain.dto.member.JoinMemberDto;
//import ohai.newslang.domain.dto.member.LoginMemberDto;
//import ohai.newslang.domain.dto.member.MemberInfoDto;
//import ohai.newslang.domain.entity.member.Member;
//import ohai.newslang.repository.member.MemberRepository;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.NoSuchElementException;
//import java.util.Optional;
//
//@Service
//@Transactional(readOnly = true)
//@RequiredArgsConstructor
//public class MemberServiceImpl implements MemberService{
//
//    private final MemberRepository memberRepository;
//    private final TokenDecoder td;
//
//    @Override
//    public void createByUser(JoinMemberDto joinMemberDto) {
//
//    }
//
//    @Override
//    public Boolean checkDuplicate(Member member) {
//        return null;
//    }
//
//    @Override
//    public void logIn(LoginMemberDto loginMemberDto) {
//
//    }
//
//    @Override
//    public MemberInfoDto readByUserInfo() {
//        Member currentMember = loadMemberByMemberId(td.currentUserId());
//        return MemberInfoDto.builder()
//                .email(currentMember.getEmail())
//                .name(currentMember.getName())
//                .imagePath(currentMember
//                        .getImagePath().isBlank() ? currentMember.getImagePath() : "Default Image Link")
//                .password(currentMember.getPassword())
//                .build();
//    }
//
//    @Override
//    public MemberInfoDto updateByUserInfo(MemberInfoDto memberInfoDto) {
//        Member currentMember = loadMemberByMemberId(td.currentUserId());
//        if (!(currentMember.getName().equals(memberInfoDto.getName()))){
//            currentMember.updateName(memberInfoDto.getName());
//        }
//
//        if (!(currentMember.getPassword().equals(memberInfoDto.getPassword()))){
//            currentMember.updateName(memberInfoDto.getName());
//        }
//
//        return MemberInfoDto.builder()
//                .email(currentMember.getEmail())
//                .name(currentMember.getName())
//                .imagePath(currentMember
//                        .getImagePath().isBlank() ? currentMember.getImagePath() : "Default Image Link")
//                .password(currentMember.getPassword())
//                .build();
//    }
//
//    @Override
//    public void deleteByUser(String password) {
//        memberRepository.deleteById(td.currentUserId());
//    }
//
//    @Override
//    @Transactional
//    public Member loadMemberByMemberId(Long id) {
//        Optional<Member> userOptional = memberRepository.findById(id);
//        if(userOptional.isPresent()) {
//            return userOptional.get();
//        } else {
//            throw new NoSuchElementException("해당 유저를 찾을 수 없습니다.");
//        }
//    }
//}
