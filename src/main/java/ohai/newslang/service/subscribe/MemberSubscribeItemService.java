package ohai.newslang.service.subscribe;

import ohai.newslang.domain.dto.request.RequestResult;
import ohai.newslang.domain.entity.subscribe.MemberSubscribeItem;
import ohai.newslang.domain.enumulate.SubscribeStatus;

import java.util.List;

public interface MemberSubscribeItemService {
    RequestResult updateSubscribeCategory(List<String> categoryNameList);
    RequestResult updateSubscribeKeyword(List<String> keywordNameList);
    RequestResult updateSubscribeMediaItems(List<String> subscribeItemNameList);
    RequestResult updateMediaSubscribeStatus(SubscribeStatus subscribeStatus);
    RequestResult updateCategorySubscribeStatus(SubscribeStatus subscribeStatus);
    RequestResult updateKeywordSubscribeStatus(SubscribeStatus subscribeStatus);
    MemberSubscribeItem getMemberSubscribeItem();
}
