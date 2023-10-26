package ohai.newslang.domain.dto.opinion.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OpinionModifyRequestDto {

    @NotBlank(message = "잘못된 수정 요청입니다.")
    private String opinionId;

    @NotBlank(message = "의견 내용을 작성해 주세요")
    @Size(max = 500,min = 1 ,message = "최소 1글자 ~ 최대 500글자로 입력해 주세요.")
    private String opinionContent;
}
