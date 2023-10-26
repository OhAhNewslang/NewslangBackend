package ohai.newslang.domain.dto.recommend.opinionRecommend;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ohai.newslang.domain.enumulate.RecommendStatus;

@Data
@NoArgsConstructor
public class OpinionRecommendDto {

    @NotBlank(message = "잘못된 등록 요청입니다.")
    private String opinionId;

    @NotBlank(message = "잘못된 등록 요청입니다.")
    private RecommendStatus status;
}
