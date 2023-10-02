package ohai.newslang.service.subscribe;

import java.util.List;

public interface MemberSubscribeItemService {
    List<String> getCategoryNameList(Long memberId);
    List<String> getKeywordNameList(Long memberId);
    List<String> getSubscribeMediaNameList(Long memberId);
    Long updateSubscribeCategory(Long memberId, List<String> categoryNameList);
    Long updateSubscribeKeyword(Long memberId, List<String> keywordNameList);
    Long updateSubscribeMediaItems(Long memberId, List<String> subscribeItemNameList);
}
