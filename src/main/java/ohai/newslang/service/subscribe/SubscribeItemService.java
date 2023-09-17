package ohai.newslang.service.subscribe;

import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.subscribe.item.SubscribeItem;
import ohai.newslang.repository.subscribe.SubscribeItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SubscribeItemService {

    private final SubscribeItemRepository subscribeItemRepository;

    public void saveSubscribeItem(List<SubscribeItem> items){
        for (SubscribeItem item : items) {
            subscribeItemRepository.save(item);
        }
    }

    public List<SubscribeItem> findSubscribeItemList(Class<?> entityType){
        List<SubscribeItem> allSubscribeItems = subscribeItemRepository.findAll(entityType);
        return allSubscribeItems;
    }

    public List<String> findNotExistNameList(List<String> nameList, Class<?> entityType){
        List<SubscribeItem> subscribeItems = this.findSubscribeItemList(entityType);
        List<String> subscribeItemNames = subscribeItems.stream()
                .map(o -> o.getName())
                .collect(Collectors.toList());
        nameList.removeAll(subscribeItemNames);
        return nameList;
    }
}
