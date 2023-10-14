package ohai.newslang.domain.dto.opinion.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ohai.newslang.domain.dto.request.RequestResult;
import ohai.newslang.domain.entity.opinion.Opinion;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class ModifyOpinionResponseDto {
    private Long opinionId;
    private String memberName;
    private String memberImagePath;
    private LocalDate opinionCreateDate;
    private String opinionContent;
    private boolean modifiable;
    private int likeCount;
    private RequestResult result;
    @Builder
    public ModifyOpinionResponseDto(Opinion opinion, RequestResult result) {
        this.opinionId = opinion.getId();
        this.memberName = opinion.getMember().getName();
        this.memberImagePath = opinion.getMember().getImagePath();
        this.opinionCreateDate = opinion.getCreateTime().toLocalDate();
        this.opinionContent = opinion.getContent();
        this.likeCount = opinion.getLikeCount();
        this.modifiable = true;
        this.result = result;
    }
}
