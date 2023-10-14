package ohai.newslang.domain.dto.opinion.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OpinionPagingRequestDtoForNews {

    private String newsUrl;
    private int pageNumber;
    private int pageSize;
}

