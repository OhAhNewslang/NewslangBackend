package ohai.newslang.domain.dto.opinion.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ohai.newslang.domain.dto.request.RequestResult;
import ohai.newslang.domain.entity.member.Member;
import ohai.newslang.domain.entity.opinion.Opinion;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class OpinionResponseDto {
    private Long opinionId;
    private String memberName;
    private String memberImagePath;
    private LocalDate opinionCreateDate;
    private String opinionContent;
    private int likeCount;
    private RequestResult result;
    @Builder
    public OpinionResponseDto(Opinion opinion, Member member, RequestResult result) {
        this.opinionId = opinion.getId();
        this.memberName = member.getName();
        this.memberImagePath = member.getImagePath();
        this.opinionCreateDate = opinion.getCreateTime().toLocalDate();
        this.opinionContent = opinion.getContent();
        this.likeCount = opinion.getLikeCount();
        this.result = result;
    }
}
