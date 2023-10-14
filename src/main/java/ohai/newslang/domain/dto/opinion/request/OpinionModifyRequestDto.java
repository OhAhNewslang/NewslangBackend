package ohai.newslang.domain.dto.opinion.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OpinionModifyRequestDto {
    private Long opinionId;
    private String opinionContent;
}
