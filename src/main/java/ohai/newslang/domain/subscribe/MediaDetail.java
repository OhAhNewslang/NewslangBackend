package ohai.newslang.domain.subscribe;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MediaDetail {
    private String mediaName;
    private String mediaImagePath;

    @Builder
    public MediaDetail(String mediaName, String mediaImagePath) {
        this.mediaName = mediaName;
        this.mediaImagePath = mediaImagePath;
    }
}
