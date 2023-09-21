package ohai.newslang.service.subscribe;

import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.subscribe.item.MediaItem;
import ohai.newslang.domain.subscribe.item.SubscribeItem;
import ohai.newslang.repository.subscribe.MediaItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MediaItemService {

    private final MediaItemRepository mediaItemRepository;

    public List<MediaItem> findSubscribeItemList(){
        return  mediaItemRepository.findAll();
    }
}
