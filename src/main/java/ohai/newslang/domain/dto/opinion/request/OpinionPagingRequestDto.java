package ohai.newslang.domain.dto.opinion.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OpinionPagingRequestDto {
    private int pageNumber;
    private int pageSize;
}

