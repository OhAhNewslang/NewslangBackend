package ohai.newslang.domain.subscribe.item;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ohai.newslang.domain.subscribe.MemberSubscribeItem;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter
@NoArgsConstructor
public abstract class SubscribeItem {

    @Id
    @GeneratedValue
    @Column(name = "subscribe_item_id")
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
