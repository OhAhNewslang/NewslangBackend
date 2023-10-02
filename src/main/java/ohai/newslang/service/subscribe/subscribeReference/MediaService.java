package ohai.newslang.service.subscribe.subscribeReference;

import ohai.newslang.domain.entity.subscribe.subscribeReference.Media;

import java.util.List;

public interface MediaService {
    void save(Media media);

    void saveAll(List<Media> mediaList);

    boolean isExistMediaName(String mediaName);

    List<Media> findAll();
}
