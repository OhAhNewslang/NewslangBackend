package ohai.newslang.domain.dto.opinion.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OpinionCreateRequestDto {
    private String newsUrl;
    private String opinionContent;
}