package ohai.newslang.domain.dto.news;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestDetailNewsDto {
    private String url;
    private String media;

    @Builder
    public RequestDetailNewsDto(String url, String media) {
        this.url = url;
        this.media = media;
    }
}
