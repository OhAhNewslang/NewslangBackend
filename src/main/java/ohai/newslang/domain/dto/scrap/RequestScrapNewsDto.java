package ohai.newslang.domain.dto.scrap;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RequestScrapNewsDto {

    private String loginId;
    private String newsUrl;
}
