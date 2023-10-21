package ohai.newslang.domain.dto.subscribe.subscribeReference;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MediaDto {
    private String mediaName;
    private String imagePath;

    @Builder
    public MediaDto(String mediaName, String imagePath) {
        this.mediaName = mediaName;
        this.imagePath = imagePath;
    }
}