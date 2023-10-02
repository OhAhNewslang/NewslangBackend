package ohai.newslang.service.subscribe.subscribeReference;

import lombok.RequiredArgsConstructor;
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
    public List<Media> findAll(){
        return  mediaRepository.findAll();
    }
}