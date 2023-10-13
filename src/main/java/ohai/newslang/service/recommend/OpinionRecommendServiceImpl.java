package ohai.newslang.service.recommend;

import lombok.RequiredArgsConstructor;
import ohai.newslang.configuration.jwt.TokenDecoder;
import ohai.newslang.domain.dto.recommend.opinionRecommend.OpinionRecommendDto;
import ohai.newslang.domain.dto.request.RequestResult;
import ohai.newslang.domain.entity.opinion.Opinion;
import ohai.newslang.domain.entity.recommend.OpinionRecommend;
import ohai.newslang.domain.enumulate.RecommendStatus;
import ohai.newslang.repository.opinion.OpinionRepository;
import ohai.newslang.repository.recommand.MemberRecommendRepository;
import ohai.newslang.repository.recommand.OpinionRecommendRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OpinionRecommendServiceImpl implements OpinionRecommendService{

    private final OpinionRepository opinionRepository;
    private final MemberRecommendRepository memberRecommendRepository;
    private final OpinionRecommendRepository opinionRecommendRepository;
    private final TokenDecoder td;

    @Override
    @Transactional
    public RequestResult updateRecommendStatus(OpinionRecommendDto opinionRecommendDto) {
        opinionRecommendRepository.findByMemberRecommend_IdAndOpinion_Id(
            memberRecommendRepository.findByMember_Id(td.currentUserId()).getId(),
            opinionRecommendDto.getOpinionId())
                .orElseGet(() ->  createRecommendInfo(opinionRecommendDto))
                .updateStatus(opinionRecommendDto.getStatus());

        return RequestResult.builder()
                .resultCode("200")
                .resultMessage("추천정보가 등록되었습니다.").build();
    }
    @Override
    @Transactional
    public OpinionRecommend createRecommendInfo(OpinionRecommendDto opinionRecommendDto) {
        OpinionRecommend opinionRecommend = OpinionRecommend.createOpinionRecommend(
                memberRecommendRepository.findByMember_Id(td.currentUserId()),
                opinionRepository.findNoOptionalById(opinionRecommendDto.getOpinionId()),
                RecommendStatus.NONE);

        return opinionRecommendRepository.save(opinionRecommend);
    }
}
