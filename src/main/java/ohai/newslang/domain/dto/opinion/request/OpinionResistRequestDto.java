package ohai.newslang.domain.dto.opinion.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
public class OpinionResistRequestDto {

    @NotBlank(message = "잘못된 등록 요청입니다.")
    private String newsUrl;

    @NotBlank(message = "의견 내용은 최소 1글자 이상 적어주세요")
    @Size(max = 500,min = 1 ,message = "최소 1글자 ~ 최대 500글자로 입력해 주세요.")
    private String opinionContent;
}
