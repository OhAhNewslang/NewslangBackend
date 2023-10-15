package ohai.newslang.domain.dto.scrap;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RequestRemoveScrapNewsDto {

    private String loginId;
    private String newsUrl;
}
