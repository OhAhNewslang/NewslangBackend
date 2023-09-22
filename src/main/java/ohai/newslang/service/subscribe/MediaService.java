package ohai.newslang.service.subscribe;

import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.subscribe.reference.Media;
import ohai.newslang.repository.subscribe.MediaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MediaService {

    private final MediaRepository mediaRepository;

    @Transactional
    public void save(Media media){
        mediaRepository.save(media);
    }

    public List<Media> findSubscribeItemList(){
        return  mediaRepository.findAll();
    }
}
