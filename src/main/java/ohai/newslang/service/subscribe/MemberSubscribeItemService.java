package ohai.newslang.service.subscribe;

import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.Member;
import ohai.newslang.domain.subscribe.MemberSubscribeItem;
import ohai.newslang.domain.subscribe.MemberSubscribeMediaItem;
import ohai.newslang.domain.subscribe.item.MediaItem;
import ohai.newslang.domain.subscribe.item.SubscribeItem;
import ohai.newslang.repository.MemberRepository;
import ohai.newslang.repository.subscribe.MediaItemRepository;
import ohai.newslang.repository.subscribe.MemberSubscribeMediaItemRepository;
import ohai.newslang.repository.subscribe.SubscribeItemRepository;
import ohai.newslang.repository.subscribe.MemberSubscribeItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberSubscribeItemService {

    private final MemberRepository memberRepository;
    private final MemberSubscribeItemRepository memberSubscribeItemRepository;
    private final SubscribeItemRepository subscribeItemRepository;
    private final MemberSubscribeMediaItemRepository memberSubscribeMediaItemRepository;
    private final MediaItemRepository mediaItemRepository;


    public List<String> findSubscribeNameList(Long memberId, Class<?> entityType) {
        if (memberSubscribeItemRepository.isExistMemberSubscribeItem(memberId)) {
            MemberSubscribeItem memberSubscribeItem = memberSubscribeItemRepository.findOne(memberId);
            Long memberSubscribeItemId = memberSubscribeItem.getId();
            // 아이템 전체 조회
            List<SubscribeItem> allSubscribeItems = subscribeItemRepository.findAllWithMemberSubscribeItemId(memberSubscribeItemId, entityType);
            return  allSubscribeItems.stream()
                    .map(o -> o.getName())
                    .collect(Collectors.toList());
        }
        return null;
    }

    public List<String> findSubscribeMediaNameList(Long memberId) {
        if (memberSubscribeItemRepository.isExistMemberSubscribeItem(memberId)) {
            MemberSubscribeItem memberSubscribeItem = memberSubscribeItemRepository.findOne(memberId);
            Long memberSubscribeItemId = memberSubscribeItem.getId();
            // 아이템 전체 조회
            List<MemberSubscribeMediaItem> memberSubscribeMediaItems = memberSubscribeMediaItemRepository.findAllWithMemberSubscribeItemId(memberSubscribeItemId);
            return  memberSubscribeMediaItems.stream()
                    .map(o -> o.getMediaItem().getName())
                    .collect(Collectors.toList());
        }
        return null;
    }

    @Transactional
    public Long updateSubscribe(Long memberId, List<String> subscribeItemNameList, Class<?> entityType) throws Exception{
        // 엔티티 조회
        Member member = memberRepository.findOne(memberId);
        MemberSubscribeItem memberSubscribeItem = null;
        if (memberSubscribeItemRepository.isExistMemberSubscribeItem(memberId)) {
            memberSubscribeItem = memberSubscribeItemRepository.findOne(memberId);
            // 기존 데이터 조회
            Long subscribeMemberItemId = memberSubscribeItem.getId();
            List<SubscribeItem> subscribeItemList = subscribeItemRepository.findAllWithMemberSubscribeItemId(subscribeMemberItemId, entityType);
            // 기존 데이터 삭제 진행
            List<Long> ids = subscribeItemList.stream()
                    .map(s -> s.getId())
                    .collect(Collectors.toList());
            memberSubscribeItem.removeSubscribeItems(ids);
//            subscribeItemRepository.deleteWithIds(ids);
        }
        else {
            memberSubscribeItem = new MemberSubscribeItem();
            memberSubscribeItem.setMember(member);
            memberSubscribeItemRepository.save(memberSubscribeItem);
        }

        memberSubscribeItem.addSubscribeItems(subscribeItemNameList, entityType);
        return memberSubscribeItem.getId();
    }

    @Transactional
    public Long updateSubscribeMedias(Long memberId, List<String> subscribeItemNameList) throws Exception{
        // 엔티티 조회
        Member member = memberRepository.findOne(memberId);
        MemberSubscribeItem memberSubscribeItem = null;
        if (memberSubscribeItemRepository.isExistMemberSubscribeItem(memberId)) {
            memberSubscribeItem = memberSubscribeItemRepository.findOne(memberId);
            // 기존 데이터 조회
            Long memberSubscribeItemId = memberSubscribeItem.getId();
            List<MemberSubscribeMediaItem> memberSubscribeMediaItems = memberSubscribeMediaItemRepository.findAllWithMemberSubscribeItemId(memberSubscribeItemId);
            // 기존 데이터 삭제 진행
            List<Long> ids = memberSubscribeMediaItems.stream()
                    .map(s -> s.getId())
                    .collect(Collectors.toList());

            memberSubscribeItem.removeMemberSubscribeMediaItems(ids);
        }
        else {
            memberSubscribeItem = new MemberSubscribeItem();
            memberSubscribeItem.setMember(member);
            memberSubscribeItemRepository.save(memberSubscribeItem);
        }

        List<MediaItem> mediaItemList = mediaItemRepository.findAllWithNameList(subscribeItemNameList);
        memberSubscribeItem.addMemberSubscribeMediaItems(mediaItemList);
        return memberSubscribeItem.getId();
    }
}
