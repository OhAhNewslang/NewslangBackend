package ohai.newslang.domain.dto.subscribe.subscribeReference;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MediaDto {
    private String mediaName;
    private String mediaImagePath;

    @Builder
    public MediaDto(String mediaName, String mediaImagePath) {
        this.mediaName = mediaName;
        this.mediaImagePath = mediaImagePath;
    }
}