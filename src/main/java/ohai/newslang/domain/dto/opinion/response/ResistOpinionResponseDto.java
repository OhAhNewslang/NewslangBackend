package ohai.newslang.domain.dto.opinion.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ohai.newslang.domain.dto.request.RequestResult;
import ohai.newslang.domain.entity.opinion.Opinion;
import ohai.newslang.domain.enumulate.RecommendStatus;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class ResistOpinionResponseDto {
    private OpinionResponseDto opinion;
    private RequestResult result;
    @Builder
    public ResistOpinionResponseDto(Opinion opinion, boolean modifiable, RecommendStatus recommend, RequestResult result) {
        this.opinion = OpinionResponseDto.builder().opinion(opinion).modifiable(modifiable).recommend(recommend).build();
        this.result = result;
    }
}
