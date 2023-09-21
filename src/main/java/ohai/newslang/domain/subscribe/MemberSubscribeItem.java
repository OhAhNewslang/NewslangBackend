package ohai.newslang.domain.subscribe;

import lombok.Getter;
import lombok.Setter;
import ohai.newslang.domain.Member;
import ohai.newslang.domain.subscribe.item.Category;
import ohai.newslang.domain.subscribe.item.Keyword;
import ohai.newslang.domain.subscribe.item.MediaItem;
import ohai.newslang.domain.subscribe.item.SubscribeItem;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
public class MemberSubscribeItem {

    @Id
    @GeneratedValue
    @Column(name = "member_subscribe_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "memberSubscribeItem", cascade = CascadeType.ALL)
    private List<SubscribeItem> subscribeItemList = new ArrayList<>();

    @OneToMany(mappedBy = "memberSubscribeItem", cascade = CascadeType.ALL)
    private List<MemberSubscribeMediaItem> memberSubscribeMediaItemList = new ArrayList<>();

    //연관관계 메서드
    public void setMember(Member member){
        this.member = member;
    }

    public void addSubscribeItems(List<String> subscribeNameList, Class<?> entityType){
        subscribeNameList.forEach(sn -> {
            SubscribeItem subscribeItem = null;
            if (entityType == Category.class){
                subscribeItem = new Category();
            }else if (entityType == Keyword.class){
                subscribeItem = new Keyword();
            }
            else return;
            subscribeItem.setName(sn);
            subscribeItem.setMemberSubscribeItem(this);
            this.subscribeItemList.add(subscribeItem);
        });
    }

    public void removeSubscribeItems(List<Long> subscribeItemIdList) {
        List<SubscribeItem> subscribeItemsToRemove = this.subscribeItemList
                .stream()
                .filter(item -> {
                    item.setMemberSubscribeItem(null);
                    return subscribeItemIdList.contains(item.getId());
                })
                .collect(Collectors.toList());

        for (SubscribeItem item : subscribeItemsToRemove) {
            this.subscribeItemList.remove(item);
        }
    }

    public void addMemberSubscribeMediaItems(List<MediaItem> mediaItemList){
        mediaItemList.forEach(sn -> {
            MemberSubscribeMediaItem msmi = new MemberSubscribeMediaItem();
            msmi.setMediaItem(sn);
            msmi.setMemberSubscribeItem(this);
            this.memberSubscribeMediaItemList.add(msmi);
        });
    }

    public void removeMemberSubscribeMediaItems(List<Long> memberSubscribeMediaItemIdList) {
        List<MemberSubscribeMediaItem> memberSubscribeMediaItems = this.memberSubscribeMediaItemList
                .stream()
                .filter(item -> {
                    item.setMediaItem(null);
                    item.setMemberSubscribeItem(null);
                    return memberSubscribeMediaItemIdList.contains(item.getId());
                })
                .collect(Collectors.toList());

        for (MemberSubscribeMediaItem item : memberSubscribeMediaItems) {
            this.memberSubscribeMediaItemList.remove(item);
        }
    }
}
