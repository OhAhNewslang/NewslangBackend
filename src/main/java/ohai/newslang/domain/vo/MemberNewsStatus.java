package ohai.newslang.domain.vo;

import lombok.Builder;
import lombok.Getter;
import ohai.newslang.domain.enumulate.RecommendStatus;

@Getter
public class MemberNewsStatus {
    private boolean isScrap;
    private String recommend;

    @Builder
    public MemberNewsStatus(boolean isScrap, RecommendStatus recommend) {
        this.isScrap = isScrap;
        this.recommend = String.valueOf(recommend);
    }
}
