package ohai.newslang.service.subscribe;

import lombok.RequiredArgsConstructor;
import ohai.newslang.configuration.jwt.TokenDecoder;
import ohai.newslang.domain.dto.request.RequestResult;
import ohai.newslang.domain.entity.member.Member;
import ohai.newslang.domain.entity.subscribe.MemberSubscribeItem;
import ohai.newslang.domain.entity.subscribe.MemberSubscribeMediaItem;
import ohai.newslang.domain.entity.subscribe.subscribeReference.Media;
import ohai.newslang.domain.enumulate.SubscribeStatus;
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
    private final SubscribeCategoryRepository subscribeCategoryRepository;
    private final SubscribeKeywordRepository subscribeKeywordRepository;
    private final TokenDecoder td;

    @Override
    @Transactional
    public RequestResult updateSubscribeCategory(List<String> categoryNameList) {
        Long memberId = td.currentMemberId();
        MemberSubscribeItem memberSubscribeItem = memberSubscribeItemRepository
        .findByMemberId(memberId)
        .orElseGet(this::createMemberSubscribeInfo);
        // 기존 카테고리 삭제 진행
        List<Long> removeIds = memberSubscribeItem.getSubscribeCategoryList().stream().map(c -> {
            c.setMemberSubscribeItem(null);
            return c.getId();
        }).collect(Collectors.toList());
        subscribeCategoryRepository.deleteAllById(removeIds);
        memberSubscribeItem.getSubscribeCategoryList().clear();
        // 새로운 카테고리 설정
        memberSubscribeItem.setMember(memberRepository.findByTokenId(memberId));
        memberSubscribeItem.addCategory(categoryNameList);
        memberSubscribeItemRepository.save(memberSubscribeItem);
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
        .findByMemberId(memberId)
        .orElseGet(this::createMemberSubscribeInfo);
        // 기존 키워드 삭제 진행
        List<Long> removeIds = memberSubscribeItem.getSubscribeKeywordList().stream().map(c -> {
            c.setMemberSubscribeItem(null);
            return c.getId();
        }).collect(Collectors.toList());
        subscribeKeywordRepository.deleteAllById(removeIds);
        memberSubscribeItem.getSubscribeKeywordList().clear();
        // 새로운 키워드 설정
        memberSubscribeItem.setMember(memberRepository.findByTokenId(memberId));
        memberSubscribeItem.addKeyword(keywordNameList);
        memberSubscribeItemRepository.save(memberSubscribeItem);
        return RequestResult.builder()
        .resultCode("200")
        .resultMessage("구독 키워드 갱신 성공")
        .build();
    }

    @Override
    @Transactional
    public RequestResult updateSubscribeMediaItems(List<String> subscribeItemNameList) {
        Long memberId = td.currentMemberId();
        // 언론사 조회
        List<Media> mediaList = mediaRepository.findByNameIn(subscribeItemNameList);
        if (mediaList.size() != subscribeItemNameList.size()){
            return RequestResult.builder()
            .resultCode("202")
            .resultMessage("존재하지 않는 언론사가 포함되었습니다.")
            .build();
        }
        MemberSubscribeItem memberSubscribeItem = memberSubscribeItemRepository
        .findByMemberId(memberId)
        .orElseGet(this::createMemberSubscribeInfo);
        // 기존 언론사 삭제 진행
        List<Long> removeIds = memberSubscribeItem.getMemberSubscribeMediaItemList().stream().map(m -> {
            m.setMedia(null);
            m.setMemberSubscribeItem(null);
            return m.getId();
        }).collect(Collectors.toList());
        memberSubscribeMediaItemRepository.deleteAllById(removeIds);
        memberSubscribeItem.getMemberSubscribeMediaItemList().clear();
        // 새로운 언론사 설정
        memberSubscribeItem.setMember(memberRepository.findByTokenId(memberId));
        memberSubscribeItem.addMemberSubscribeMediaItems(mediaList);
        memberSubscribeItemRepository.save(memberSubscribeItem);

        return RequestResult.builder()
                .resultCode("200")
                .resultMessage("구독 언론사 갱신 성공")
                .build();
    }

    @Override
    @Transactional
    public RequestResult updateMediaSubscribeStatus(SubscribeStatus subscribeStatus) {
        Long memberId = td.currentMemberId();
        MemberSubscribeItem memberSubscribeItem = memberSubscribeItemRepository.findByMemberId(memberId).orElseThrow();
        memberSubscribeItem.setMediaSubscribeStatus(subscribeStatus);
        return RequestResult.builder()
                .resultCode("200")
                .resultMessage("언론사 구독 상태 갱신 성공")
                .build();
    }

    @Override
    public RequestResult updateCategorySubscribeStatus(SubscribeStatus subscribeStatus) {
        Long memberId = td.currentMemberId();
        MemberSubscribeItem memberSubscribeItem = memberSubscribeItemRepository.findByMemberId(memberId).orElseThrow();
        memberSubscribeItem.setCategorySubscribeStatus(subscribeStatus);
        return RequestResult.builder()
                .resultCode("200")
                .resultMessage("카테고리 구독 상태 갱신 성공")
                .build();
    }

    @Override
    public RequestResult updateKeywordSubscribeStatus(SubscribeStatus subscribeStatus) {
        Long memberId = td.currentMemberId();
        MemberSubscribeItem memberSubscribeItem = memberSubscribeItemRepository.findByMemberId(memberId).orElseThrow();
        memberSubscribeItem.setKeywordSubscribeStatus(subscribeStatus);
        return RequestResult.builder()
                .resultCode("200")
                .resultMessage("키워드 구독 상태 갱신 성공")
                .build();
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
                .findByMemberId(td.currentMemberId())
                .orElseGet(this::createMemberSubscribeInfo);
    }
}