package ohai.newslang.domain.entity.subscribe;

import lombok.Getter;

import jakarta.persistence.*;
import ohai.newslang.domain.enumulate.SubscribeStatus;

@Entity
@Getter
public class SubscribeCategory {

    @Id
    @GeneratedValue
    @Column(name = "subscribe_category_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_subscribe_item_id")
    private MemberSubscribeItem memberSubscribeItem;

    public void setName(String name){
        this.name = name;
    }

    public void setMemberSubscribeItem(MemberSubscribeItem memberSubscribeItem){
        this.memberSubscribeItem = memberSubscribeItem;
    }
}