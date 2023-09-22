package ohai.newslang.domain.subscribe;

import lombok.Getter;
import ohai.newslang.domain.subscribe.MemberSubscribeItem;

import javax.persistence.*;

@Entity
@Getter
public class SubscribeKeyword {

    @Id
    @GeneratedValue
    @Column(name = "subscribe_keyword_id")
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
