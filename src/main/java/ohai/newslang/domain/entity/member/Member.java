package ohai.newslang.domain.entity.member;

import jakarta.persistence.*;
import lombok.*;
import ohai.newslang.domain.entity.TimeStamp;
import ohai.newslang.domain.enumulate.UserRole;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

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
    private String email;

    @Column(length = 100, nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private String imagePath;
    @Builder
    public Member(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        // 회원 가입시 기본은 유저 권한
        role = UserRole.ROLE_USER;
        // 회원 가입시 기본 이미지
        imagePath = "DefaultImagePath";
    }

    //연관 관계 메서드

    public Member(String name, String email, String password, UserRole role, String imagePath) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.imagePath = imagePath;
    }

    //비즈니스 로직
    public void updateName(String newName) {
        name = newName;
    }

    public void updatePassword(String newPassword) {
        password = newPassword;
    }

    public void updateRoles(UserRole newRole){
        role = newRole;
    }

    public void updateImagePath(String newImagePath) {
        imagePath = newImagePath;
    }
}
