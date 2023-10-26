package ohai.newslang.domain.dto.opinion.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OpinionDeleteRequestDto {
    @NotBlank(message = "잘못된 요청 입니다.")
    String opinionId;
}
