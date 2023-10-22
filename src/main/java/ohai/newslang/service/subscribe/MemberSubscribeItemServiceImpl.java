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
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberSubscribeItemServiceImpl implements MemberSubscribeItemService {

    private final MemberRepository memberRepository;
    private final MemberSubscribeItemRepository memberSubscribeItemRepository;
    private final MemberSubscribeMediaItemRepository memberSubscribeMediaItemRepository;
    private final MediaRepository mediaRepository;

    @Override
    @Transactional
    public void updateSubscribeCategory(Long memberId, List<String> categoryNameList) {
        MemberSubscribeItem memberSubscribeItem = memberSubscribeItemRepository.findByMemberId(memberId).orElse(null);
        if (memberSubscribeItem == null){
            Member member = memberRepository.findById(memberId).get();
            memberSubscribeItem = new MemberSubscribeItem();
            memberSubscribeItem.setMember(member);
            memberSubscribeItemRepository.save(memberSubscribeItem);
        }
        else {
            memberSubscribeItem.clearCategory();
        }

        memberSubscribeItem.addCategory(categoryNameList);
    }

    @Override
    @Transactional
    public void updateSubscribeKeyword(Long memberId, List<String> keywordNameList) {
        MemberSubscribeItem memberSubscribeItem = memberSubscribeItemRepository.findByMemberId(memberId).orElse(null);
        if (memberSubscribeItem == null){
            Member member = memberRepository.findById(memberId).get();
            memberSubscribeItem = new MemberSubscribeItem();
            memberSubscribeItem.setMember(member);
            memberSubscribeItemRepository.save(memberSubscribeItem);
        }
        else {
            memberSubscribeItem.clearKeyword();
        }

        memberSubscribeItem.addKeyword(keywordNameList);
    }

    @Override
    @Transactional
    public void updateSubscribeMediaItems(Long memberId, List<String> subscribeItemNameList) throws Exception {
        List<Media> mediaList = mediaRepository.findByNameIn(subscribeItemNameList);
        if (mediaList.size() < 1) throw new Exception("Not exist media");
        // findOne -> findById + get() 메서드(Optional개봉)
        MemberSubscribeItem memberSubscribeItem = memberSubscribeItemRepository.findByMemberId(memberId).orElse(null);
        if (memberSubscribeItem == null){
            Member member = memberRepository.findById(memberId).get();
            memberSubscribeItem = new MemberSubscribeItem();
            memberSubscribeItem.setMember(member);
            memberSubscribeItemRepository.save(memberSubscribeItem);
        }
        else {
            // 기존 데이터 조회
            Long memberSubscribeItemId = memberSubscribeItem.getId();
            List<MemberSubscribeMediaItem> memberSubscribeMediaItems = memberSubscribeMediaItemRepository.findByMemberSubscribeItemId(memberSubscribeItemId);
            // 기존 데이터 삭제 진행
            List<Long> ids = memberSubscribeMediaItems.stream()
                    .map(s -> s.getId())
                    .collect(Collectors.toList());
            memberSubscribeItem.removeMemberSubscribeMediaItems(ids);
        }
        memberSubscribeItem.addMemberSubscribeMediaItems(mediaList);
    }

    @Override
    public MemberSubscribeItem getMemberSubscribeItem(Long memberId) {
        return memberSubscribeItemRepository.findByMemberId(memberId).orElseGet(MemberSubscribeItem::new);
    }
}