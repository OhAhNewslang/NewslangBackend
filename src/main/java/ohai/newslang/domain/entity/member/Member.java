package ohai.newslang.domain.entity.member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ohai.newslang.domain.entity.TimeStamp;

import java.util.ArrayList;
import java.util.Arrays;
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
    private String email;

    @Column(length = 100, nullable = false)
    private String password;

    private String roles;

    private String imagePath;
    //연관 관계 메서드

    public Member(String name, String email, String password, String roles, String imagePath) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.imagePath = imagePath;
    }

    //비즈니스 로직
    public void updateName(String newName) {
        name = newName;
    }

    public void updatePassword(String newPassword) {
        password = newPassword;
    }

    public void updateRoles(String newRoles){
        if (roles == null) roles = "";
        if (!roles.isEmpty()) roles += ",";
        roles += newRoles;
    }

    public void updateImagePath(String newImagePath) {
        imagePath = newImagePath;
    }

    public List<String> getRoleList(){
        if (!this.roles.isEmpty()) return Arrays.asList(roles.split(","));
        return new ArrayList<>();
    }
}
