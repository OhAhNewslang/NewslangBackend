package ohai.newslang.domain.entity.scrap;

import lombok.Getter;
import ohai.newslang.domain.entity.member.Member;
import ohai.newslang.domain.entity.news.NewsArchive;

import jakarta.persistence.*;
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

    @ManyToOne(fetch = FetchType.LAZY)
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

    public void removeMemberScrapNewsArchive(List<String> urlList){
        List<MemberScrapNewsArchive> removeList = new ArrayList<>();
        this.memberScrapNewsArchiveList.forEach(n ->{
            String newsUrl = n.getNewsArchive().getNews().getUrl();
            if (urlList.contains(newsUrl)){
                n.setMemberScrapNews(null);
                n.setNewsArchive(null);
                removeList.add(n);
            }
        });
        this.memberScrapNewsArchiveList.removeAll(removeList);
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
