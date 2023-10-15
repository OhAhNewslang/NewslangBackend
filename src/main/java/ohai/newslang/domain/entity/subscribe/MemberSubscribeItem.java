package ohai.newslang.domain.entity.subscribe;

import lombok.Getter;
import ohai.newslang.domain.entity.member.Member;
import ohai.newslang.domain.entity.subscribe.MemberSubscribeMediaItem;
import ohai.newslang.domain.entity.subscribe.SubscribeCategory;
import ohai.newslang.domain.entity.subscribe.SubscribeKeyword;
import ohai.newslang.domain.entity.subscribe.subscribeReference.Media;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
public class MemberSubscribeItem {

    @Id
    @GeneratedValue
    @Column(name = "member_subscribe_item_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "memberSubscribeItem", cascade = CascadeType.ALL)
    private List<SubscribeCategory> subscribeCategoryList = new ArrayList<>();

    @OneToMany(mappedBy = "memberSubscribeItem", cascade = CascadeType.ALL)
    private List<SubscribeKeyword> subscribeKeywordList = new ArrayList<>();

    @OneToMany(mappedBy = "memberSubscribeItem", cascade = CascadeType.ALL)
    private List<MemberSubscribeMediaItem> memberSubscribeMediaItemList = new ArrayList<>();

    //연관관계 메서드
    public void setMember(Member member){
        this.member = member;
    }

    public void addCategory(List<String> nameList){
        nameList.forEach(sn -> {
            SubscribeCategory subscribeCategory = new SubscribeCategory();
            subscribeCategory.setName(sn);
            subscribeCategory.setMemberSubscribeItem(this);
            this.subscribeCategoryList.add(subscribeCategory);
        });
    }

    public void addKeyword(List<String> nameList){
        nameList.forEach(sn -> {
            SubscribeKeyword subscribeKeyword = new SubscribeKeyword();
            subscribeKeyword.setName(sn);
            subscribeKeyword.setMemberSubscribeItem(this);
            this.subscribeKeywordList.add(subscribeKeyword);
        });
    }

    public void clearCategory() {
        for (SubscribeCategory item : this.subscribeCategoryList) {
            item.setMemberSubscribeItem(null);
        }
        this.subscribeCategoryList.clear();
    }

    public void clearKeyword() {
        for (SubscribeKeyword item : this.subscribeKeywordList) {
            item.setMemberSubscribeItem(null);
        }
        this.subscribeKeywordList.clear();
    }

    public void addMemberSubscribeMediaItems(List<Media> mediaList){
        mediaList.forEach(sn -> {
            MemberSubscribeMediaItem msmi = new MemberSubscribeMediaItem();
            msmi.setMedia(sn);
            msmi.setMemberSubscribeItem(this);
            this.memberSubscribeMediaItemList.add(msmi);
        });
    }

    public void removeMemberSubscribeMediaItems(List<Long> memberSubscribeMediaItemIdList) {
        this.memberSubscribeMediaItemList.removeIf(m ->{
            if (memberSubscribeMediaItemIdList.contains(m.getId())){
                m.setMedia(null);
                m.setMemberSubscribeItem(null);
                return true;
            }
            return false;
        });
//        List<MemberSubscribeMediaItem> memberSubscribeMediaItems = this.memberSubscribeMediaItemList
//                .stream()
//                .filter(item -> {
//                    item.setMedia(null);
//                    item.setMemberSubscribeItem(null);
//                    return memberSubscribeMediaItemIdList.contains(item.getId());
//                })
//                .collect(Collectors.toList());
//        this.memberSubscribeMediaItemList.removeAll(memberSubscribeMediaItems);
    }
}