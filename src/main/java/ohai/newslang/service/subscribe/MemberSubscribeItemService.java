package ohai.newslang.service.subscribe;

import ohai.newslang.domain.dto.request.RequestResult;
import ohai.newslang.domain.entity.subscribe.MemberSubscribeItem;

import java.util.List;

public interface MemberSubscribeItemService {
    RequestResult updateSubscribeCategory(List<String> categoryNameList);
    RequestResult updateSubscribeKeyword(List<String> keywordNameList);
    RequestResult updateSubscribeMediaItems(List<String> subscribeItemNameList);
    MemberSubscribeItem getMemberSubscribeItem();
}
