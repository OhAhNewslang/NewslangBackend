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
    private final OpinionRecommendRepository opinionRecommendRepository;
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
    public List<OpinionResponseDto> opinionListByDetailNews(Long detailNewsId) {
        return opinionRepository.findOpinionDtosByDetailNewArchiveId(detailNewsId)
                .stream().map(o -> new OpinionResponseDto(o,
                    opinionRecommendRepository.countAllByOpinion_IdAndStatus(o.getId(),RecommendStatus.LIKE),
                    RequestResult.builder()
                        .resultCode("200")
                        .resultMessage("뉴스 의견 목록 조회")
                        .build())).collect(Collectors.toList());
    }

    @Override
    public List<OpinionResponseDto> opinionListByMember() {
        return opinionRepository.findOpinionDtosByMemberId(td.currentUserId())
                .stream().map(o -> new OpinionResponseDto(o,
                    opinionRecommendRepository.countAllByOpinion_IdAndStatus(o.getId(),RecommendStatus.LIKE),
                    RequestResult.builder()
                        .resultCode("200")
                        .resultMessage("뉴스 의견 목록 조회")
                        .build())).collect(Collectors.toList());
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
        findOpinion.deleteOpinion();
        opinionRepository.delete(findOpinion);
        return RequestResult.builder()
                .resultCode("200")
                .resultMessage("의견 삭제 완료").build();
    }
}
