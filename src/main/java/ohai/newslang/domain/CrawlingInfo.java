package ohai.newslang.domain;

import lombok.Getter;
import net.bytebuddy.asm.Advice;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class CrawlingInfo {

    @Id
    @GeneratedValue
    @Column(name = "crawling_info_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private LocalDateTime lookupStartDate;

    private LocalDateTime lookupEndDate;
}
