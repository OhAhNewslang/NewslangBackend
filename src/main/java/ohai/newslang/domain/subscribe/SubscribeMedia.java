package ohai.newslang.domain.subscribe;

import lombok.Getter;
import lombok.Setter;
import ohai.newslang.domain.Media;
import ohai.newslang.domain.Member;

import javax.persistence.*;

@Entity
@Getter
public class SubscribeMedia {

    @Id @GeneratedValue
    @Column(name = "subscribe_media_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "media_id")
    private Media media;
}
