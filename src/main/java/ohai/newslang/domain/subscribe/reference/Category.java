package ohai.newslang.domain.subscribe.reference;

import lombok.Getter;
import ohai.newslang.domain.subscribe.MemberSubscribeItem;

import javax.persistence.*;

@Entity
@Getter
public class Category {

    @Id
    @GeneratedValue
    @Column(name = "subscribe_category_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    public void setName(String name){
        this.name = name;
    }
}
