package ohai.newslang.domain.scrap;

import lombok.Getter;
import ohai.newslang.domain.Member;
import ohai.newslang.domain.NewsArchive;
import ohai.newslang.domain.subscribe.MemberSubscribe;
import ohai.newslang.domain.subscribe.MemberSubscribeItem;
import ohai.newslang.domain.subscribe.item.SubscribeItem;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class MemberScrapNews {

    @Id
    @GeneratedValue
    @Column(name = "member_scrap_news_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "memberScrapNews", cascade = CascadeType.ALL)
    private List<MemberScrapNewsArchive> memberScrapNewsArchiveList = new ArrayList<>();

    public void setMember(Member member){
        this.member = member;
    }

    public void newMemberScrapNewsArchive(MemberScrapNewsArchive memberScrapNewsArchive){
        this.memberScrapNewsArchiveList.add(memberScrapNewsArchive);
        memberScrapNewsArchive.setMemberScrapNews(this);
    }

    public static MemberScrapNews newMemberScrapNews(MemberScrapNews memberScrapNews, Member member, NewsArchive newsArchive){
        if (memberScrapNews == null) {
            memberScrapNews = new MemberScrapNews();
            memberScrapNews.setMember(member);
        }

        MemberScrapNewsArchive memberScrapNewsArchive = new MemberScrapNewsArchive();
        memberScrapNewsArchive.setScrapDate(LocalDate.now());
        memberScrapNewsArchive.setNewsArchive(newsArchive);

        memberScrapNews.newMemberScrapNewsArchive(memberScrapNewsArchive);
        return memberScrapNews;
    }
}
