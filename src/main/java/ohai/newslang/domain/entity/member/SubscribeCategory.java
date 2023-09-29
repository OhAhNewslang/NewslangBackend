package ohai.newslang.domain.entity.member;

import lombok.Getter;

import javax.persistence.*;

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