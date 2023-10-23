package ohai.newslang.domain.dto.opinion.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import ohai.newslang.domain.dto.page.RequestPageSourceDto;

@Data
@NoArgsConstructor
public class OpinionPagingRequestDtoForNews {

    @NotBlank(message = "잘못된 페이지 요청입니다.")
    private String newsUrl;

    private RequestPageSourceDto pageSourceDto;
}

