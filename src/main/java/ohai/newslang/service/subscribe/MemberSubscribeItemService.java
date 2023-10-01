package ohai.newslang.service.subscribe;

import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.entity.member.Member;
import ohai.newslang.domain.entity.subscribe.MemberSubscribeItem;
import ohai.newslang.domain.entity.subscribe.MemberSubscribeMediaItem;
import ohai.newslang.domain.entity.subscribe.subscribeReference.Media;
import ohai.newslang.repository.member.MemberRepository;
import ohai.newslang.repository.subscribe.MemberSubscribeItemRepository;
import ohai.newslang.repository.subscribe.MemberSubscribeMediaItemRepository;
import ohai.newslang.repository.subscribe.subscribeReference.MediaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberSubscribeItemService {

    private final MemberRepository memberRepository;
    private final MemberSubscribeItemRepository memberSubscribeItemRepository;

    private final MemberSubscribeMediaItemRepository memberSubscribeMediaItemRepository;
    private final MediaRepository mediaRepository;

    public List<String> findCategoryNameList(Long memberId) {
        if (memberSubscribeItemRepository.countByMemberId(memberId) > 0) {
            MemberSubscribeItem memberSubscribeItem = memberSubscribeItemRepository.findByMemberId(memberId);
            return memberSubscribeItem.getSubscribeCategoryList().stream()
                    .map(c -> c.getName())
                    .collect(Collectors.toList());
        }
        return null;
    }

    public List<String> findKeywordNameList(Long memberId) {
        if (memberSubscribeItemRepository.countByMemberId(memberId) > 0) {
            MemberSubscribeItem memberSubscribeItem = memberSubscribeItemRepository.findByMemberId(memberId);
            return memberSubscribeItem.getSubscribeKeywordList().stream()
                    .map(c -> c.getName())
                    .collect(Collectors.toList());
        }
        return null;
    }

    public List<String> findSubscribeMediaNameList(Long memberId) {
        if (memberSubscribeItemRepository.countByMemberId(memberId) > 0) {
            MemberSubscribeItem memberSubscribeItem = memberSubscribeItemRepository.findByMemberId(memberId);
            Long memberSubscribeItemId = memberSubscribeItem.getId();
            // 아이템 전체 조회
            List<MemberSubscribeMediaItem> memberSubscribeMediaItems = memberSubscribeMediaItemRepository.findByMemberSubscribeItemId(memberSubscribeItemId);
            return  memberSubscribeMediaItems.stream()
                    .map(o -> o.getMedia().getName())
                    .collect(Collectors.toList());
        }
        return null;
    }

    @Transactional
    public Long updateSubscribeCategory(Long memberId, List<String> categoryNameList) {
        // 엔티티 조회
        Member member = memberRepository.findById(memberId).get();
        // findOne -> findById + get() 메서드(Optional개봉)
        MemberSubscribeItem memberSubscribeItem = null;
        if (memberSubscribeItemRepository.countByMemberId(memberId) > 0) {
            memberSubscribeItem = memberSubscribeItemRepository.findByMemberId(memberId);
            memberSubscribeItem.clearCategory();
        }
        else {
            memberSubscribeItem = new MemberSubscribeItem();
            memberSubscribeItem.setMember(member);
            memberSubscribeItemRepository.save(memberSubscribeItem);
        }

        memberSubscribeItem.addCategory(categoryNameList);
        return memberSubscribeItem.getId();
    }

    @Transactional
    public Long updateSubscribeKeyword(Long memberId, List<String> keywordNameList) {
        // 엔티티 조회
        Member member = memberRepository.findById(memberId).get();
        // findOne -> findById + get() 메서드(Optional개봉)
        MemberSubscribeItem memberSubscribeItem = null;
        if (memberSubscribeItemRepository.countByMemberId(memberId) > 0) {
            memberSubscribeItem = memberSubscribeItemRepository.findByMemberId(memberId);
            memberSubscribeItem.clearKeyword();
        }
        else {
            memberSubscribeItem = new MemberSubscribeItem();
            memberSubscribeItem.setMember(member);
            memberSubscribeItemRepository.save(memberSubscribeItem);
        }

        memberSubscribeItem.addKeyword(keywordNameList);
        return memberSubscribeItem.getId();
    }

    @Transactional
    public Long updateSubscribeMedias(Long memberId, List<String> subscribeItemNameList) throws Exception{
        // 엔티티 조회
        Member member = memberRepository.findById(memberId).get();
        // findOne -> findById + get() 메서드(Optional개봉)
        MemberSubscribeItem memberSubscribeItem = null;
        if (memberSubscribeItemRepository.countByMemberId(memberId) > 0) {
            memberSubscribeItem = memberSubscribeItemRepository.findByMemberId(memberId);
            // 기존 데이터 조회
            Long memberSubscribeItemId = memberSubscribeItem.getId();
            List<MemberSubscribeMediaItem> memberSubscribeMediaItems = memberSubscribeMediaItemRepository.findByMemberSubscribeItemId(memberSubscribeItemId);
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

        List<Media> mediaList = mediaRepository.findByNameIn(subscribeItemNameList);
        memberSubscribeItem.addMemberSubscribeMediaItems(mediaList);
        return memberSubscribeItem.getId();
    }
}