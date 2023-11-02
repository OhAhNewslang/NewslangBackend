package ohai.newslang.domain.entity.member;

import jakarta.persistence.*;
import lombok.*;
import ohai.newslang.domain.dto.member.request.JoinMemberDto;
import ohai.newslang.domain.entity.TimeStamp;
import ohai.newslang.domain.entity.opinion.Opinion;
import ohai.newslang.domain.entity.recommend.MemberRecommend;
import ohai.newslang.domain.enumulate.UserRole;
import org.springframework.security.crypto.password.PasswordEncoder;

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

    @Column(length = 100, nullable = false)
    private String loginId;

    @Column(length = 100, nullable = false)
    private String email;

    @Column(length = 100, nullable = false)
    private String password;

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL)
    private MemberRecommend memberRecommend;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Opinion> opinions = new ArrayList<>();

    // 연관 관계 메서드
    public void foreignMemberRecommend(MemberRecommend newMemberRecommend) {
        memberRecommend = newMemberRecommend;
        memberRecommend.foreignMember(this);
    }

    //비즈니스 로직
    public static Member createMember(MemberRecommend newMemberRecommend,
                                      String newName,
                                      String newLoginId,
                                      String newEmail,
                                      String newPassword){
        Member member = new Member();

        member.name = newName;
        member.loginId =newLoginId;
        member.email = newEmail;
        member.password = newPassword;
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
}
