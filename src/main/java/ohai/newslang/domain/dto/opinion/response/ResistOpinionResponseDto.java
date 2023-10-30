package ohai.newslang.domain.dto.opinion.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ohai.newslang.domain.dto.request.RequestResult;
import ohai.newslang.domain.entity.opinion.Opinion;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class ResistOpinionResponseDto {
    private OpinionResponseDto opinion;
    private RequestResult result;
    @Builder
    public ResistOpinionResponseDto(Opinion opinion, boolean modifiable, RequestResult result) {
        this.opinion = OpinionResponseDto.builder().opinion(opinion).modifiable(modifiable).build();
        this.result = result;
    }
}
