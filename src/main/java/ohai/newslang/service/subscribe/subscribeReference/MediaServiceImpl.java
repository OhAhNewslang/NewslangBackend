package ohai.newslang.service.subscribe.subscribeReference;

import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.dto.subscribe.subscribeReference.MediaDto;
import ohai.newslang.domain.entity.subscribe.subscribeReference.Media;
import ohai.newslang.repository.subscribe.subscribeReference.MediaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MediaServiceImpl implements MediaService{

    private final MediaRepository mediaRepository;

    @Override
    @Transactional
    public void save(Media media){
        mediaRepository.save(media);
    }


    @Override
    public boolean isExistMediaName(String mediaName){
        return mediaRepository.countByName(mediaName) > 0;
    }

    @Override
    public List<MediaDto> findAll(){
        return  mediaRepository.findAll().stream()
        .map(m -> MediaDto.builder()
        .mediaName(m.getName())
        .imagePath(m.getImagePath())
        .build()).toList();
    }
}