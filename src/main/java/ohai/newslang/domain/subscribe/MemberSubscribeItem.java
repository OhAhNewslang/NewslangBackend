package ohai.newslang.domain.subscribe;

import lombok.Getter;
import lombok.Setter;
import ohai.newslang.domain.subscribe.item.SubscribeItem;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class MemberSubscribeItem {

    @Id
    @GeneratedValue
    @Column(name = "member_subscribe_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subscribe_item_id")
    private SubscribeItem subscribeItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_subscribe_id")
    private MemberSubscribe memberSubscribe;
}
