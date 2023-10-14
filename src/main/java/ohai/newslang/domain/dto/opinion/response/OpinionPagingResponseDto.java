package ohai.newslang.domain.dto.opinion.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ohai.newslang.domain.dto.request.RequestResult;
import org.springframework.data.domain.Slice;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OpinionPagingResponseDto {
    private Slice<OpinionResponseDto> opinions;
    private RequestResult result;

}
