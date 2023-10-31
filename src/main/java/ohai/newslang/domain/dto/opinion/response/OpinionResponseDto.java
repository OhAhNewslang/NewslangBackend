package ohai.newslang.domain.dto.opinion.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ohai.newslang.domain.dto.page.ResponsePageSourceDto;
import ohai.newslang.domain.dto.request.RequestResult;
import ohai.newslang.domain.entity.opinion.Opinion;
import ohai.newslang.domain.enumulate.RecommendStatus;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class OpinionResponseDto {
    private String opinionId;
    private String memberName;
    private LocalDate opinionCreateDate;
    private String opinionContent;
    private boolean modifiable;
    private int likeCount;
    private String recommend;
    @Builder
    public OpinionResponseDto(Opinion opinion,boolean modifiable,RecommendStatus recommend) {
        this.opinionId = opinion.getUuid();
        this.memberName = opinion.getMember().getName();
        this.opinionCreateDate = opinion.getCreateTime().toLocalDate();
        this.opinionContent = opinion.getContent();
        this.likeCount = opinion.getLikeCount();
        this.modifiable = modifiable;
        this.recommend = String.valueOf(recommend);
    }
}
