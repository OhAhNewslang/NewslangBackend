package ohai.newslang.domain.dto.recommend.newsRecommend;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ohai.newslang.domain.enumulate.RecommendStatus;

@Data
@NoArgsConstructor
public class NewsRecommendDto {
    private String newsUrl;
    private RecommendStatus status;
}
