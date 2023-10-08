package ohai.newslang.domain.dto.recommend.opinionRecommend;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ohai.newslang.domain.enumulate.RecommendStatus;

@Data
@NoArgsConstructor
public class OpinionRecommendDto {
    private Long opinionId;
    private RecommendStatus status;
}
