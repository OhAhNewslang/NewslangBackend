package ohai.newslang.domain.dto.scrap;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RequestScrapNewsPageDto {
    private String loginId;
    private int page;
    private int limit;
}
