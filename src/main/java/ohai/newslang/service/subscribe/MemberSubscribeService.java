package ohai.newslang.service.subscribe;

import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.Member;
import ohai.newslang.domain.subscribe.MemberSubscribe;
import ohai.newslang.domain.subscribe.MemberSubscribeItem;
import ohai.newslang.domain.subscribe.item.SubscribeItem;
import ohai.newslang.repository.MemberRepository;
import ohai.newslang.repository.subscribe.SubscribeItemRepository;
import ohai.newslang.repository.subscribe.MemberSubscribeItemRepository;
import ohai.newslang.repository.subscribe.MemberSubscribeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberSubscribeService {

    private final MemberRepository memberRepository;
    private final MemberSubscribeRepository memberSubscribeRepository;
    private final MemberSubscribeItemRepository memberSubscribeItemRepository;
    private final SubscribeItemRepository subscribeItemRepository;

    public List<String> findSubscribeList(Long memberId, Class<?> entityType) throws Exception {
        if (memberSubscribeRepository.isExistMemberSubscribe(memberId)) {
            MemberSubscribe memberSubscribe = memberSubscribeRepository.findOne(memberId);
            Long subscribeMemberId = memberSubscribe.getId();
            // 아이템 전체 조회
            List<SubscribeItem> allSubscribeItems = subscribeItemRepository.findAll(entityType);
            List<Long> subscribeItemIds = allSubscribeItems.stream()
                    .map(o -> o.getId())
                    .collect(Collectors.toList());
            // 전체 중 해당되는 아이템만 추출
            List<MemberSubscribeItem> memberSubscribeItems = memberSubscribeItemRepository.findAllWithMemberSubscribeId(subscribeMemberId, subscribeItemIds);
            return memberSubscribeItems.stream()
                    .map(o -> o.getSubscribeItem().getName())
                    .collect(Collectors.toList());
        }
        return null;
    }

    @Transactional
    public Long updateSubscribe(Long memberId, List<String> subscribeItemIdList, Class<?> entityType) throws Exception{
        // 엔티티 조회
        MemberSubscribe memberSubscribe = null;
        if (memberSubscribeRepository.isExistMemberSubscribe(memberId)) {
            memberSubscribe = memberSubscribeRepository.findOne(memberId);
            // 기존 데이터 삭제 진행
            // 기존 데이터 조회
            Long subscribeMemberId = memberSubscribe.getId();
            List<SubscribeItem> allSubscribeItems = subscribeItemRepository.findAll(entityType);
            List<Long> subscribeItemIds = allSubscribeItems.stream()
                    .map(o -> o.getId())
                    .collect(Collectors.toList());
            // 구독 사용자 아이디의 엔티티타입 데이터 전체 삭제
            memberSubscribeItemRepository.delete(subscribeMemberId, subscribeItemIds);
            memberSubscribe = memberSubscribeRepository.findOne(memberId);
        }
        Member member = memberRepository.findOne(memberId);
        // 아이템 정보 추출
        List<SubscribeItem> itemList = subscribeItemRepository.findAllIdWithName(subscribeItemIdList, entityType);
        memberSubscribe = MemberSubscribe.newMemberSubscribe(memberSubscribe, member, itemList);
        memberSubscribeRepository.save(memberSubscribe);
        return memberSubscribe.getId();
    }
}
