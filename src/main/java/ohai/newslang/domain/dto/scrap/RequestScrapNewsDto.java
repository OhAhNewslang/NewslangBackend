package ohai.newslang.domain.dto.scrap;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RequestScrapNewsDto {

    @NotBlank(message = "잘못된 요청입니다.")
    private String newsUrl;

}
