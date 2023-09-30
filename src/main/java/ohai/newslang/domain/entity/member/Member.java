package ohai.newslang.domain.entity.member;

import lombok.Getter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Getter
//@Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(length = 100, nullable = false)
    private String name;

    private String password;

    private String roles;

    private String imagePath;

    private LocalDateTime joinDate;

    public void setName(String name){
        this.name = name;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setRoles(String roles){
        if (this.roles == null) this.roles = "";
        if (this.roles != "") this.roles += ",";
        this.roles += roles;
    }

    public void setJoinDate(LocalDateTime joinDate){
        this.joinDate = joinDate;
    }

    public List<String> getRoleList(){
        if (this.roles.length() > 0){
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }
}
