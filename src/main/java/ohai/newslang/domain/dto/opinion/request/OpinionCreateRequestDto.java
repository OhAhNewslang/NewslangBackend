package ohai.newslang.domain.dto.opinion.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OpinionCreateRequestDto {
    private Long detailNewsId;
    private String opinionContent;
}
