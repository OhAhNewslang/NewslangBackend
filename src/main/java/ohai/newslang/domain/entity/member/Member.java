package ohai.newslang.domain.entity.member;

import jakarta.persistence.*;
import lombok.*;
import ohai.newslang.domain.dto.member.request.JoinMemberDto;
import ohai.newslang.domain.entity.TimeStamp;
import ohai.newslang.domain.entity.opinion.Opinion;
import ohai.newslang.domain.entity.recommend.MemberRecommend;
import ohai.newslang.domain.enumulate.UserRole;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends TimeStamp {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(length = 100, nullable = false)
    private String name;

    private String loginId;

    @Column(length = 100, nullable = false)
    private String email;

    @Column(length = 100, nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private String imagePath;

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL)
    private MemberRecommend memberRecommend;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Opinion> opinions = new ArrayList<>();

    // 생성자
    @Builder
    public Member(String name, String loginId, String email, String password, MemberRecommend memberRecommend) {
        this.name = name;
        this.loginId = loginId;
        this.email = email;
        this.password = password;
        this.imagePath = "DefaultImagePath";
        this.role = UserRole.ROLE_USER;
        foreignMemberRecommend(memberRecommend);
    }




    // 연관 관계 메서드
    public void foreignMemberRecommend(MemberRecommend newMemberRecommend) {
        memberRecommend = newMemberRecommend;
        memberRecommend.foreignMember(this);
    }

    //비즈니스 로직
    public static Member createMember(MemberRecommend newMemberRecommend, JoinMemberDto joinMemberDto){
        Member member = new Member();
        member.name = joinMemberDto.getName();
        member.loginId = joinMemberDto.getLoginId();
        member.email = joinMemberDto.getEmail();
        member.password = joinMemberDto.getPassword();
        member.imagePath = "DefaultImagePath";
        member.foreignMemberRecommend(newMemberRecommend);


        return member;
    }
    public void updateName(String newName) {
        name = newName;
    }

    public void updatePassword(String newPassword) {
        password = newPassword;
    }

    public void updateEmail(String newEmail){
        email = newEmail;
    }
    public void updateRoles(UserRole newRole){
        role = newRole;
    }

    public void updateImagePath(String newImagePath) {
        imagePath = newImagePath;
    }
}
