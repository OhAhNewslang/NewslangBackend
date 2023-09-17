package ohai.newslang.domain.subscribe;

import lombok.Getter;
import ohai.newslang.domain.Member;
import ohai.newslang.domain.subscribe.item.SubscribeItem;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class MemberSubscribe {

    @Id @GeneratedValue
    @Column(name = "member_subscribe_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "memberSubscribe", cascade = CascadeType.ALL)
    private List<MemberSubscribeItem> memberSubscribeItems = new ArrayList<>();

    //연관관계 메서드
    public void setMember(Member member){
        this.member = member;
    }

    public void newMemberSubscribeItems(List<MemberSubscribeItem> memberSubscribeItemList){
        for (MemberSubscribeItem item : memberSubscribeItemList) {
            this.memberSubscribeItems.add(item);
            item.setMemberSubscribe(this);
        }
    }

    public static MemberSubscribe newMemberSubscribe(MemberSubscribe memberSubscribe, Member member, List<SubscribeItem> itemList){
        if (memberSubscribe == null) {
            memberSubscribe = new MemberSubscribe();
            memberSubscribe.setMember(member);
        }
        List<MemberSubscribeItem> memberSubscribeItemList = new ArrayList<>();
        for (SubscribeItem item : itemList) {
            MemberSubscribeItem memberSubscribeItem = new MemberSubscribeItem();
            memberSubscribeItem.setSubscribeItem(item);
            memberSubscribeItemList.add(memberSubscribeItem);
        }
        memberSubscribe.newMemberSubscribeItems(memberSubscribeItemList);
        return memberSubscribe;
    }
}
