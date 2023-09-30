package ohai.newslang.domain.entity.member;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import ohai.newslang.domain.entity.TimeStamp;

import java.time.LocalDateTime;
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

    //비즈니스 로직

    //연관 관계 메서드

    // 보안 메서드
    public void setRoles(String roles){
        if (this.roles == null) this.roles = "";
        else if (!this.roles.isEmpty()) this.roles += ",";
        this.roles += roles;
    }

    public List<String> getRoleList(){
        if (!this.roles.isEmpty()) return Arrays.asList(this.roles.split(","));
        return new ArrayList<>();
    }
}
