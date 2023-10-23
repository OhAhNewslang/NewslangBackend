package ohai.newslang.domain.dto.recommend.newsRecommend;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ohai.newslang.domain.enumulate.RecommendStatus;

@Data
@NoArgsConstructor
public class NewsRecommendDto {

    @NotBlank(message = "잘못된 등록 요청입니다.")
    private String newsUrl;

    @NotBlank(message = "잘못된 등록 요청입니다.")
    private RecommendStatus status;
}
