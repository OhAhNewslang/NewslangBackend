package ohai.newslang.domain.subscribe;

import lombok.Getter;
import ohai.newslang.domain.Member;

import javax.persistence.*;

@Entity
@Getter
public class SubscribeCategory {

    @Id @GeneratedValue
    @Column(name = "subscribe_category_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String category;
}
