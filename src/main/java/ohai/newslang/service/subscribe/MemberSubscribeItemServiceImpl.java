package ohai.newslang.service.subscribe;

import lombok.RequiredArgsConstructor;
import ohai.newslang.configuration.jwt.TokenDecoder;
import ohai.newslang.domain.entity.member.Member;
import ohai.newslang.domain.entity.subscribe.MemberSubscribeItem;
import ohai.newslang.domain.entity.subscribe.MemberSubscribeMediaItem;
import ohai.newslang.domain.entity.subscribe.SubscribeCategory;
import ohai.newslang.domain.entity.subscribe.SubscribeKeyword;
import ohai.newslang.domain.entity.subscribe.subscribeReference.Media;
import ohai.newslang.repository.member.MemberRepository;
import ohai.newslang.repository.subscribe.MemberSubscribeItemRepository;
import ohai.newslang.repository.subscribe.MemberSubscribeMediaItemRepository;
import ohai.newslang.repository.subscribe.subscribeReference.MediaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberSubscribeItemServiceImpl implements MemberSubscribeItemService {

    private final MemberRepository memberRepository;
    private final MemberSubscribeItemRepository memberSubscribeItemRepository;
    private final MemberSubscribeMediaItemRepository memberSubscribeMediaItemRepository;
    private final MediaRepository mediaRepository;
    private final TokenDecoder td;

    @Override
    @Transactional
    public Long updateSubscribeCategory(Long memberId, List<String> categoryNameList) {
        // 엔티티 조회
        Member member = memberRepository.findByTokenId(memberId);
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

    @Override
    @Transactional
    public Long updateSubscribeKeyword(Long memberId, List<String> keywordNameList) {
        // 엔티티 조회
        Member member = memberRepository.findByTokenId(memberId);
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

    @Override
    @Transactional
    public Long updateSubscribeMediaItems(Long memberId, List<String> subscribeItemNameList) {
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
            Member member = memberRepository.findByTokenId(memberId);
            memberSubscribeItem = new MemberSubscribeItem();
            memberSubscribeItem.setMember(member);
            memberSubscribeItemRepository.save(memberSubscribeItem);
        }

        List<Media> mediaList = mediaRepository.findByNameIn(subscribeItemNameList);
        memberSubscribeItem.addMemberSubscribeMediaItems(mediaList);
        return memberSubscribeItem.getId();
    }

    @Override
    public MemberSubscribeItem getMemberSubscribeItem() {
        return memberSubscribeItemRepository.findByMemberId(td.currentUserId());
    }
}