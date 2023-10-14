package ohai.newslang.service.opinion;

import lombok.RequiredArgsConstructor;
import ohai.newslang.configuration.jwt.TokenDecoder;
import ohai.newslang.domain.dto.opinion.request.OpinionCreateRequestDto;
import ohai.newslang.domain.dto.opinion.request.OpinionModifyRequestDto;
import ohai.newslang.domain.dto.opinion.response.ModifyOpinionResponseDto;
import ohai.newslang.domain.dto.opinion.response.OpinionResponseDto;
import ohai.newslang.domain.dto.opinion.response.OpinionPagingResponseDto;
import ohai.newslang.domain.dto.request.RequestResult;
import ohai.newslang.domain.entity.opinion.Opinion;
import ohai.newslang.repository.member.MemberRepository;
import ohai.newslang.repository.news.DetailNewsArchiveRepository;
import ohai.newslang.repository.opinion.OpinionRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OpinionServiceImpl implements OpinionService{
    private final MemberRepository memberRepository;
    private final OpinionRepository opinionRepository;
    private final DetailNewsArchiveRepository detailNewsArchiveRepository;
    private final TokenDecoder td;

    @Override
    @Transactional
    public RequestResult resistOpinion(OpinionCreateRequestDto opinionCreateRequestDto) {
        Opinion newOpinion = Opinion.createOpinion(
                memberRepository.findByTokenId(td.currentUserId()),
                detailNewsArchiveRepository.findNoOptionalById(opinionCreateRequestDto.getDetailNewsId()),
                opinionCreateRequestDto.getOpinionContent()
        );
        opinionRepository.save(newOpinion);
        return RequestResult.builder()
                .resultCode("201")
                .resultMessage("의견 등록 완료").build();
    }

    @Override
    public OpinionPagingResponseDto opinionListByLikeCountOrderForDetailNews(
            String newsUrl,
            int pageNumber,
            int pageSize) {
        // Pageable 인터페이스의 구현체 PageRequest
        // pageNumber -> 현재 몇 페이지 인지
        // pageSize -> 몇개 까지 출력할 것 인지

        // 추천수 페이징 정렬 조건
        PageRequest sortCondition = PageRequest
                .of(pageNumber,
                    pageSize,
                    Sort.by(Sort.Direction.DESC,"likeCount"));

        return OpinionPagingResponseDto.builder()
                .opinions(
                    opinionRepository.findAllByDetailNewsArchiveUrl(
                        newsUrl,
                        sortCondition
                    ).map(opinion ->
                        OpinionResponseDto.builder()
                            .opinion(opinion)
                            .modifiable(opinion.getMember().getId().equals(td.currentUserId()))
                            .build()
                    )
                ).result(RequestResult.builder()
                        .resultCode("200")
                        .resultMessage("뉴스 댓글 목록 조회 성공")
                        .build()
                ).build();
    }

    @Override
    public OpinionPagingResponseDto opinionListByRecentOrderForDetailNews(
            String newsUrl,
            int pageNumber,
            int pageSize) {
        // Pageable 인터페이스의 구현체 PageRequest
        // pageNumber -> 현재 몇 페이지 인지
        // pageSize -> 몇개 까지 출력할 것 인지

        // 추천수 페이징 정렬 조건
        PageRequest sortCondition = PageRequest
                .of(pageNumber,
                    pageSize,
                    Sort.by(Sort.Direction.DESC,"createTime"));

        return OpinionPagingResponseDto.builder()
                .opinions(
                    opinionRepository.findAllByDetailNewsArchive_Id(
                        detailNewsArchiveRepository.findNewsIdByNewsUrl(newsUrl),
                        sortCondition
                    ).map(opinion ->
                        OpinionResponseDto.builder()
                            .opinion(opinion)
                            .modifiable(opinion.getMember().getId().equals(td.currentUserId()))
                            .build()
                    )
                ).result(RequestResult.builder()
                    .resultCode("200")
                    .resultMessage("뉴스 댓글 목록 조회 성공")
                    .build()
                ).build();
    }

    @Override
    public OpinionPagingResponseDto opinionListByLikeCountOrderForMember(
            int pageNumber,
            int pageSize) {
        //Pageable 인터페이스의 구현체 PageRequest
        // 추천수 페이징 정렬 조건
        PageRequest sortCondition = PageRequest
                .of(pageNumber,
                    pageSize,
                    Sort.by(Sort.Direction.DESC,"likeCount"));

        return OpinionPagingResponseDto.builder()
                .opinions(
                    opinionRepository.findAllByMemberId(td.currentUserId(), sortCondition)
                    .map(opinion ->
                        OpinionResponseDto.builder()
                            .opinion(opinion)
                            .modifiable(true)
                            .build()
                        )
                    ).result(RequestResult.builder()
                    .resultCode("200")
                    .resultMessage("뉴스 댓글 목록 조회 성공")
                    .build()
                ).build();
    }

    @Override
    public OpinionPagingResponseDto opinionListByRecentOrderForMember(int pageNumber, int pageSize) {
        //Pageable 인터페이스의 구현체 PageRequest
        // 추천수 페이징 정렬 조건
        PageRequest sortCondition = PageRequest
                .of(0,
                        3,
                        Sort.by(Sort.Direction.DESC,"createTime"));

        return OpinionPagingResponseDto.builder()
                .opinions(
                    opinionRepository.findAllByMemberId(td.currentUserId(), sortCondition)
                        .map(opinion ->
                        OpinionResponseDto.builder()
                            .opinion(opinion)
                            .modifiable(true)
                            .build()
                        )
                ).result(RequestResult.builder()
                    .resultCode("200")
                    .resultMessage("뉴스 댓글 목록 조회 성공")
                    .build()
                ).build();
    }

    @Override
    @Transactional
    public ModifyOpinionResponseDto modifyContent(OpinionModifyRequestDto opinionModifyRequestDto) {
        opinionRepository.findNoOptionalById(opinionModifyRequestDto.getOpinionId())
                .updateContent(opinionModifyRequestDto.getOpinionContent());
        return ModifyOpinionResponseDto.builder()
                .opinion(opinionRepository.findNoOptionalJoinMemberById(
                        opinionModifyRequestDto.getOpinionId())
                ).result(RequestResult.builder()
                        .resultCode("200")
                        .resultMessage("댓글 수정 완료")
                        .build())
                .build();

    }

    @Override
    @Transactional
    public RequestResult deleteOpinion(Long opinionId) {
        Opinion findOpinion = opinionRepository.findNoOptionalById(opinionId);
        Opinion eraseForeignKeyOpinion = findOpinion.deleteOpinion();
        opinionRepository.delete(eraseForeignKeyOpinion);
        return RequestResult.builder()
                .resultCode("200")
                .resultMessage("의견 삭제 완료").build();
    }
}
