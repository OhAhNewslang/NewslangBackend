package ohai.newslang.service.subscribe;

import lombok.RequiredArgsConstructor;
import ohai.newslang.configuration.jwt.TokenDecoder;
import ohai.newslang.domain.dto.request.RequestResult;
import ohai.newslang.domain.entity.member.Member;
import ohai.newslang.domain.entity.subscribe.MemberSubscribeItem;
import ohai.newslang.domain.entity.subscribe.MemberSubscribeMediaItem;
import ohai.newslang.domain.entity.subscribe.subscribeReference.Media;
import ohai.newslang.repository.member.MemberRepository;
import ohai.newslang.repository.subscribe.MemberSubscribeItemRepository;
import ohai.newslang.repository.subscribe.MemberSubscribeMediaItemRepository;
import ohai.newslang.repository.subscribe.SubscribeCategoryRepository;
import ohai.newslang.repository.subscribe.SubscribeKeywordRepository;
import ohai.newslang.repository.subscribe.subscribeReference.MediaRepository;
import org.springframework.boot.autoconfigure.amqp.RabbitRetryTemplateCustomizer;
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
    public RequestResult updateSubscribeCategory(List<String> categoryNameList) {
        Long memberId = td.currentMemberId();
        MemberSubscribeItem memberSubscribeItem = memberSubscribeItemRepository
        .findSubscribeCategoryByMemberId(memberId)
        .orElseGet(MemberSubscribeItem::new);

        memberSubscribeItem.setMember(memberRepository.findByTokenId(memberId));
        memberSubscribeItem.clearCategory();

        memberSubscribeItemRepository.save(memberSubscribeItem);
        memberSubscribeItem.addCategory(categoryNameList);
        return RequestResult.builder()
        .resultCode("200")
        .resultMessage("구독 카테고리 갱신 성공")
        .build();
    }

    @Override
    @Transactional
    public RequestResult updateSubscribeKeyword(List<String> keywordNameList) {
        Long memberId = td.currentMemberId();
        MemberSubscribeItem memberSubscribeItem = memberSubscribeItemRepository
        .findSubscribeKeywordByMemberId(memberId).orElseGet(MemberSubscribeItem::new);
        memberSubscribeItem.setMember(memberRepository.findByTokenId(memberId));
        memberSubscribeItem.clearKeyword();
        memberSubscribeItemRepository.save(memberSubscribeItem);
        memberSubscribeItem.addKeyword(keywordNameList);
        return RequestResult.builder()
        .resultCode("200")
        .resultMessage("구독 카테고리 갱신 성공")
        .build();
    }

    @Override
    @Transactional
    public RequestResult updateSubscribeMediaItems(List<String> subscribeItemNameList) {
        Long memberId = td.currentMemberId();
        List<Media> mediaList = mediaRepository.findByNameIn(subscribeItemNameList);
        if (mediaList.size() != subscribeItemNameList.size()){
            return RequestResult.builder()
            .resultCode("202")
            .resultMessage("존재하지 않는 언론사가 포함되었습니다.")
            .build();
        }

        MemberSubscribeItem memberSubscribeItem = memberSubscribeItemRepository
        .findSubscribeMediaByMemberId(memberId)
        .orElseGet(this::createMemberSubscribeInfo);

        memberSubscribeItem
        .removeMemberSubscribeMediaItems(memberSubscribeItem
        .getMemberSubscribeMediaItemList().stream()
        .map(MemberSubscribeMediaItem::getId).toList());

        return RequestResult.builder()
                .resultCode("200")
                .resultMessage("구독 언론사 갱신 성공")
                .build();
    }

    private MemberSubscribeMediaItem subscribeMedia(Media media) {
        MemberSubscribeMediaItem memberSubscribeMediaItem = new MemberSubscribeMediaItem();
        memberSubscribeMediaItem.setMedia(media);

        return memberSubscribeMediaItemRepository
                .save(memberSubscribeMediaItem);
    }
    private MemberSubscribeItem createMemberSubscribeInfo(){
        MemberSubscribeItem memberSubscribeItem = new MemberSubscribeItem();
        memberSubscribeItem.setMember(memberRepository.findByTokenId(td.currentMemberId()));
        return memberSubscribeItemRepository.save(memberSubscribeItem);
    }

    // 확인
    @Override
    public MemberSubscribeItem getMemberSubscribeItem() {
        return memberSubscribeItemRepository
        .findSubscribeMediaByMemberId(td.currentMemberId())
        .orElseGet(MemberSubscribeItem::new);
    }
}