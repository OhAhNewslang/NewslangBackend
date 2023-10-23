package ohai.newslang.service.subscribe.subscribeReference;

import ohai.newslang.domain.dto.subscribe.subscribeReference.MediaDto;
import ohai.newslang.domain.entity.subscribe.subscribeReference.Media;

import java.util.List;

public interface MediaService {
    void save(Media media);

    boolean isExistMediaName(String mediaName);

    List<MediaDto> findAll();
}
