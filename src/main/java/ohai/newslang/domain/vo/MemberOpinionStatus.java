package ohai.newslang.domain.vo;

import lombok.Builder;
import lombok.Getter;
import ohai.newslang.domain.enumulate.RecommendStatus;

@Getter
public class MemberOpinionStatus {
    private boolean modifiable;
    private String recommend;

    @Builder
    public MemberOpinionStatus(boolean modifiable, RecommendStatus recommend) {
        this.modifiable = modifiable;
        this.recommend = String.valueOf(recommend);
    }
}
