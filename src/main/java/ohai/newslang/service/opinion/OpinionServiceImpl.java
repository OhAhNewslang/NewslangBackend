package ohai.newslang.service.opinion;

import lombok.RequiredArgsConstructor;
import ohai.newslang.configuration.jwt.TokenDecoder;
import ohai.newslang.domain.dto.opinion.request.OpinionCreateRequestDto;
import ohai.newslang.domain.dto.opinion.request.OpinionModifyRequestDto;
import ohai.newslang.domain.dto.opinion.response.OpinionResponseDto;
import ohai.newslang.domain.dto.request.RequestResult;
import ohai.newslang.domain.entity.opinion.Opinion;
import ohai.newslang.domain.enumulate.RecommendStatus;
import ohai.newslang.repository.member.MemberRepository;
import ohai.newslang.repository.news.DetailNewsArchiveRepository;
import ohai.newslang.repository.opinion.OpinionRepository;
import ohai.newslang.repository.recommand.OpinionRecommendRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
    public Slice<OpinionResponseDto> opinionListByDetailNews(Long detailNewsId) {
        //Pageable 인터페이스의 구현체 PageRequest
        // 최신순 페이징 정렬 조건
        PageRequest pageRequestByRecentOrder = PageRequest.of(0,3, Sort.by(Sort.Direction.DESC,"createTime"));
        // 추천수 페이징 정렬 조건
        PageRequest pageRequestByLikeCountOrder = PageRequest.of(0,3, Sort.by(Sort.Direction.DESC,"likeCount"));

        return opinionRepository.findAllByDetailNewsArchive_Id(detailNewsId, pageRequestByLikeCountOrder)
                .map(opinion -> new OpinionResponseDto(
                    opinion,
                    opinion.getMember(),
                    RequestResult.builder()
                        .resultCode("200")
                        .resultMessage("멤버 댓글 목록 조회 성공").build()));
    }

    @Override
    public Slice<OpinionResponseDto> opinionListByMember() {
        //Pageable 인터페이스의 구현체 PageRequest
        // 최신순 페이징 정렬 조건
        PageRequest pageRequestByRecentOrder = PageRequest.of(0,3, Sort.by(Sort.Direction.DESC,"createTime"));
        // 추천수 페이징 정렬 조건
        PageRequest pageRequestByLikeCountOrder = PageRequest.of(0,3, Sort.by(Sort.Direction.DESC,"likeCount"));

        return opinionRepository.findAllByMemberId(td.currentUserId(),pageRequestByLikeCountOrder)
                .map(opinion -> new OpinionResponseDto(
                    opinion,
                    opinion.getMember(),
                    RequestResult.builder()
                        .resultCode("200")
                        .resultMessage("멤버 댓글 목록 조회 성공").build()));
    }

    @Override
    @Transactional
    public OpinionResponseDto modifyContent(OpinionModifyRequestDto opinionModifyRequestDto) {
        opinionRepository.findNoOptionalById(opinionModifyRequestDto.getOpinionId())
                .updateContent(opinionModifyRequestDto.getOpinionContent());
        return null;
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
