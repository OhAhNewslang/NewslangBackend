package ohai.newslang.dto.subscribe;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ohai.newslang.domain.subscribe.MediaDetail;
import ohai.newslang.domain.RequestResult;

import java.util.List;

@Getter
@NoArgsConstructor
public class ResultSubscribeMediaDto {

    private int mediaCount;
    private List<MediaDetail> mediaList;
    private RequestResult result;

    @Builder
    public ResultSubscribeMediaDto(List<MediaDetail> mediaList, RequestResult result) {
        this.mediaCount = mediaList.size();
        this.mediaList = mediaList;
        this.result = result;
    }
}
