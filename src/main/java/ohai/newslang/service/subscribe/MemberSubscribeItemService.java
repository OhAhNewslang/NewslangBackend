package ohai.newslang.service.subscribe;

import ohai.newslang.domain.entity.subscribe.MemberSubscribeItem;

import java.util.List;

public interface MemberSubscribeItemService {
    void updateSubscribeCategory(Long memberId, List<String> categoryNameList);
    void updateSubscribeKeyword(Long memberId, List<String> keywordNameList);
    void updateSubscribeMediaItems(Long memberId, List<String> subscribeItemNameList) throws Exception;
    MemberSubscribeItem getMemberSubscribeItem(Long memberId);
}
