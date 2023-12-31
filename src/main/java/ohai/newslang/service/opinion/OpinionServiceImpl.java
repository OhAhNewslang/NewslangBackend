package ohai.newslang.service.opinion;

import lombok.RequiredArgsConstructor;
import ohai.newslang.configuration.jwt.TokenDecoder;
import ohai.newslang.domain.dto.opinion.request.OpinionModifyRequestDto;
import ohai.newslang.domain.dto.opinion.request.OpinionResistRequestDto;
import ohai.newslang.domain.dto.opinion.response.*;
import ohai.newslang.domain.dto.page.ResponsePageSourceDto;
import ohai.newslang.domain.dto.request.RequestResult;
import ohai.newslang.domain.entity.opinion.Opinion;
import ohai.newslang.domain.entity.recommend.NewsRecommend;
import ohai.newslang.domain.entity.recommend.OpinionRecommend;
import ohai.newslang.domain.enumulate.RecommendStatus;
import ohai.newslang.domain.vo.MemberOpinionStatus;
import ohai.newslang.repository.member.MemberRepository;
import ohai.newslang.repository.news.NewsArchiveRepository;
import ohai.newslang.repository.opinion.OpinionRepository;
import ohai.newslang.repository.recommand.MemberRecommendRepository;
import ohai.newslang.repository.recommand.OpinionRecommendRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OpinionServiceImpl implements OpinionService{
    private final MemberRepository memberRepository;
    private final OpinionRepository opinionRepository;
    private final NewsArchiveRepository newsArchiveRepository;
    private final OpinionRecommendRepository opinionRecommendRepository;
    private final MemberRecommendRepository memberRecommendRepository;
    private final TokenDecoder td;

    @Override
    @Transactional
    public ResistOpinionResponseDto resistOpinion(OpinionResistRequestDto opinionResistRequestDto) {

        Long currentUserId = td.currentMemberId();
        Opinion newOpinion = Opinion.createOpinion(
        memberRepository.findByTokenId(currentUserId),
        newsArchiveRepository.findByUrl(opinionResistRequestDto.getNewsUrl()),
        opinionResistRequestDto.getOpinionContent());

        OpinionRecommend opinionRecommend = OpinionRecommend.createOpinionRecommend(
                memberRecommendRepository.findByMember_Id(td.currentMemberId()),
                opinionRepository.findNoOptionalByUuid(newOpinion.getUuid()),
                RecommendStatus.NONE);

        opinionRecommendRepository.save(opinionRecommend);

        Opinion savedOpinion = opinionRepository.save(newOpinion);
        return ResistOpinionResponseDto.builder()
                .opinion(savedOpinion)
                .modifiable(savedOpinion.getMember().getId().equals(currentUserId))
                .recommend(opinionRecommend.getStatus())
                .result(RequestResult.builder()
                .resultCode("201")
                .resultMessage("의견 등록 완료").build()).build();
    }

    @Override
    public MemberOpinionStatusDto opinionStatusForNews(
            String newsUrl) {

        List<Opinion> allByDetailNewsArchiveUrl = opinionRepository.findAllByDetailNewsArchiveUrl(newsUrl);

        MemberOpinionStatusDto build = MemberOpinionStatusDto.builder()
                .memberOpinionStatusList(allByDetailNewsArchiveUrl.stream().map(o ->
                        MemberOpinionStatus.builder()
                                .opinionId(o.getUuid())
                                .modifiable(o.getMember().getId().equals(td.currentMemberId()))
                                .recommend(opinionRecommendRepository
                                        .findByMemberRecommend_IdAndOpinion_Uuid(memberRecommendRepository
                                                .findByMember_Id(td.currentMemberId()).getId(), o.getUuid())
                                        .orElse(OpinionRecommend.getNoneRecommend()).getStatus())
                                .build()).collect(Collectors.toList()))
                .build();

        return build;
    }

    @Override
    public OpinionListResponseDto opinionListByLikeCountOrderForDetailNews(
            String newsUrl, int pageNumber, int pageSize) {

        // Pageable 인터페이스의 구현체 PageRequest
        // pageNumber -> 현재 몇 페이지 인지
        // pageSize -> 몇개 까지 출력할 것 인지

        // 추천수 페이징 정렬 조건
        PageRequest pageRequest = PageRequest
        .of(pageNumber - 1, pageSize, Sort.by(Sort.Direction.DESC,"likeCount"));

        return getOpinionListResponseDto(newsUrl, pageRequest);
    }

    @Override
    public OpinionListResponseDto opinionListByRecentOrderForDetailNews(
            String newsUrl, int pageNumber, int pageSize) {

        // Pageable 인터페이스의 구현체 PageRequest
        // pageNumber -> 현재 몇 페이지 인지
        // pageSize -> 몇개 까지 출력할 것 인지

        // 추천수 페이징 정렬 조건
        PageRequest pageRequest = PageRequest
        .of(pageNumber - 1, pageSize, Sort.by(Sort.Direction.DESC,"createTime"));

        return getOpinionListResponseDto(newsUrl, pageRequest);
    }

    // 상세 뉴스용 의견 페이징 -> 이번 상세뉴스의 의견 목록 api 호출에 해당하는 페이지 리스트
    private OpinionListResponseDto getOpinionListResponseDto(String newsUrl, PageRequest pageRequest) {
        Page<Opinion> findOpinions = opinionRepository.findAllByDetailNewsArchiveUrl(newsUrl, pageRequest);
        return OpinionListResponseDto.builder()
                .opinions(findOpinions
                .map(o -> OpinionResponseDto.builder()
                .opinion(o)
                .build()).toList())
                .pageSource(ResponsePageSourceDto.builder()
                .page(pageRequest.getPageNumber() + 1)
                .limit(pageRequest.getPageSize())
                .totalPage(findOpinions.getTotalPages()).build())
                .result(RequestResult.builder()
                .resultCode("200")
                .resultMessage("뉴스 댓글 목록 조회 성공")
                .build()).build();
    }

    @Override
    public OpinionListResponseDto opinionListByLikeCountOrderForMember(
            int pageNumber, int pageSize) {

        //Pageable 인터페이스의 구현체 PageRequest
        // 추천수 페이징 정렬 조건
        PageRequest pageRequest = PageRequest
        .of(pageNumber - 1, pageSize, Sort.by(Sort.Direction.DESC,"likeCount"));

        return getOpinionListResponseDto(pageRequest);
    }

    @Override
    public OpinionListResponseDto opinionListByRecentOrderForMember(
            int pageNumber, int pageSize) {

        //Pageable 인터페이스의 구현체 PageRequest
        // 추천수 페이징 정렬 조건
        PageRequest pageRequest = PageRequest
        .of(pageNumber - 1, pageSize, Sort.by(Sort.Direction.DESC,"createTime"));

        return getOpinionListResponseDto(pageRequest);
    }

    // 의견 모아보기용 의견 페이징 -> 이번 회원의 의견 모아보기 api 호출에 해당하는 페이지 리스트
    private OpinionListResponseDto getOpinionListResponseDto(PageRequest pageRequest) {
        Page<Opinion> findOpinions = opinionRepository
                .findAllByMemberId(td.currentMemberId(),pageRequest);

        return OpinionListResponseDto.builder()
                .opinions(findOpinions.stream()
                .map(o -> OpinionResponseDto.builder()
                .opinion(o)
                .modifiable(true)
                .recommend(opinionRecommendRepository
                .findByMemberRecommend_IdAndOpinion_Uuid(memberRecommendRepository
                .findByMember_Id(td.currentMemberId()).getId(), o.getUuid())
                .orElse(OpinionRecommend.getNoneRecommend()).getStatus())
                .build()).toList())
                .pageSource(ResponsePageSourceDto.builder()
                .page(pageRequest.getPageNumber() + 1)
                .limit(pageRequest.getPageSize())
                .totalPage(findOpinions.getTotalPages()).build())
                .result(RequestResult.builder()
                .resultCode("200")
                .resultMessage("뉴스 댓글 목록 조회 성공")
                .build()).build();
    }

    @Override
    @Transactional
    public ModifyOpinionResponseDto modifyContent(OpinionModifyRequestDto opinionModifyRequestDto) {

        opinionRepository.findNoOptionalByUuid(opinionModifyRequestDto.getOpinionId())
                .updateContent(opinionModifyRequestDto.getOpinionContent());

        return ModifyOpinionResponseDto.builder()
                .opinion(opinionRepository
                .findNoOptionalJoinMemberByUuid(opinionModifyRequestDto.getOpinionId()))
                .result(RequestResult.builder()
                .resultCode("200")
                .resultMessage("댓글 수정 완료")
                .build()).build();

    }

    @Override
    @Transactional
    public RequestResult deleteOpinion(String opinionId) {

        Opinion findOpinion = opinionRepository.findNoOptionalByUuid(opinionId);
        opinionRepository.delete(findOpinion.deleteOpinion());
        return RequestResult.builder()
                .resultCode("200")
                .resultMessage("의견 삭제 완료").build();
    }
}
