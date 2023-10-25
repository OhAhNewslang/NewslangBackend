package ohai.newslang.service.recommend;

import lombok.RequiredArgsConstructor;
import ohai.newslang.configuration.jwt.TokenDecoder;
import ohai.newslang.domain.dto.recommend.newsRecommend.NewsRecommendDto;
import ohai.newslang.domain.dto.request.RequestResult;
import ohai.newslang.domain.entity.recommend.NewsRecommend;
import ohai.newslang.domain.enumulate.RecommendStatus;
import ohai.newslang.repository.news.NewsArchiveRepository;
import ohai.newslang.repository.recommand.MemberRecommendRepository;
import ohai.newslang.repository.recommand.NewsRecommendRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NewsRecommendServiceImpl implements NewsRecommendService {

    private final NewsArchiveRepository newsArchiveRepository;
    private final MemberRecommendRepository memberRecommendRepository;
    private final NewsRecommendRepository newsRecommendRepository;
    private final TokenDecoder td;

    @Override
    @Transactional
    public RequestResult updateRecommendStatus(NewsRecommendDto newsRecommendDto) {
        newsRecommendRepository.findByMemberRecommend_IdAndDetailNewsArchiveUrl(
                memberRecommendRepository.findByMember_Id(
                    td.currentMemberId()).getId(),
                    newsRecommendDto.getNewsUrl()
                ).orElseGet(() -> createRecommendInfo(newsRecommendDto)
                ).updateStatus(newsRecommendDto.getStatus());

        return RequestResult.builder()
                .resultCode("200")
                .resultMessage("추천정보가 등록되었습니다.").build();
    }

    @Override
    @Transactional
    public NewsRecommend createRecommendInfo(NewsRecommendDto newsRecommendDto) {
        // 뉴스 추천정보 만들기 도메인 로직 호출
        NewsRecommend newsRecommend = NewsRecommend.createNewsRecommend(
            // 멤버 추천 정보, 디테일 뉴스 영속성 부여
            memberRecommendRepository.findByMember_Id(td.currentMemberId()),
            newsArchiveRepository.findByUrl(newsRecommendDto.getNewsUrl()),
            RecommendStatus.NONE
        );

        return newsRecommendRepository.save(newsRecommend);
    }
}
