package ohai.newslang.domain.dto.opinion.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ohai.newslang.domain.dto.page.ResponsePageSourceDto;
import ohai.newslang.domain.dto.request.RequestResult;
import org.springframework.data.domain.Slice;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OpinionListResponseDto {
    private List<OpinionResponseDto> opinions;
    private ResponsePageSourceDto pageSource;
    private RequestResult result;
}