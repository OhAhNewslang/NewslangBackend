package ohai.newslang.domain.vo;

import lombok.Builder;
import lombok.Getter;
import ohai.newslang.domain.enumulate.RecommendStatus;

@Getter
public class MemberOpinionStatus {
    private String opinionId;
    private boolean modifiable;
    private String recommend;

    @Builder
    public MemberOpinionStatus(String opinionId, boolean modifiable, RecommendStatus recommend) {
        this.opinionId = opinionId;
        this.modifiable = modifiable;
        this.recommend = String.valueOf(recommend);
    }
}
