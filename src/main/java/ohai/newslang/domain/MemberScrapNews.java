package ohai.newslang.domain;

import lombok.Getter;

import javax.persistence.*;

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

    @Embedded
    private News news;
}
