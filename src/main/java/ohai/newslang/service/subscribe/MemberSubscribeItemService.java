package ohai.newslang.service.subscribe;

import ohai.newslang.domain.entity.subscribe.MemberSubscribeItem;

import java.util.List;

public interface MemberSubscribeItemService {
    Long updateSubscribeCategory(Long memberId, List<String> categoryNameList);
    Long updateSubscribeKeyword(Long memberId, List<String> keywordNameList);
    Long updateSubscribeMediaItems(Long memberId, List<String> subscribeItemNameList);
    MemberSubscribeItem getMemberSubscribeItem();
}
