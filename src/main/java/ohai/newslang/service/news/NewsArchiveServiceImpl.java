package ohai.newslang.service.news;

import lombok.RequiredArgsConstructor;
import ohai.newslang.configuration.jwt.TokenDecoder;
import ohai.newslang.domain.dto.news.DetailNewsDto;
import ohai.newslang.domain.dto.news.ResponseThumbnailNewsDto;
import ohai.newslang.domain.dto.news.ResultDetailNewsDto;
import ohai.newslang.domain.dto.news.ThumbnailNewsDto;
import ohai.newslang.domain.dto.page.ResponsePageSourceDto;
import ohai.newslang.domain.dto.request.RequestResult;
import ohai.newslang.domain.entity.news.NewsArchive;
import ohai.newslang.domain.entity.recommend.NewsRecommend;
import ohai.newslang.domain.entity.scrap.MemberScrapNews;
import ohai.newslang.domain.entity.scrap.MemberScrapNewsArchive;
import ohai.newslang.domain.entity.subscribe.MemberSubscribeItem;
import ohai.newslang.domain.entity.subscribe.SubscribeCategory;
import ohai.newslang.domain.entity.subscribe.SubscribeKeyword;
import ohai.newslang.repository.news.NewsArchiveRepository;
import ohai.newslang.repository.recommand.MemberRecommendRepository;
import ohai.newslang.repository.recommand.NewsRecommendRepository;
import ohai.newslang.repository.scrap.MemberScrapNewsRepository;
import ohai.newslang.repository.subscribe.MemberSubscribeItemRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NewsArchiveServiceImpl implements NewsArchiveService{

    private final NewsArchiveRepository newsArchiveRepository;
    private final MemberSubscribeItemRepository subscribeItemRepository;
    private final NewsRecommendRepository newsRecommendRepository;
    private final MemberRecommendRepository memberRecommendRepository;
    private final MemberScrapNewsRepository memberScrapNewsRepository;
    private final TokenDecoder td;

    // 크롤링에 사용 되는 뉴스 서비스 1
    @Override
    @Transactional
    public void saveAll(List<NewsArchive> newsArchiveList){
        newsArchiveRepository.saveAll(newsArchiveList);
    }

    // 크롤링에 사용 되는 뉴스 서비스 2
    @Override
    public List<String> isAlreadyExistUrl(List<String> urlList){
        return newsArchiveRepository.alreadyExistByUrl(urlList);
    }

    @Override
    public ResultDetailNewsDto findByUrl(String url){
        NewsArchive findDetailNews = newsArchiveRepository.findNewsArchiveByUrl(url);

        return ResultDetailNewsDto.builder()
        .detailNews(DetailNewsDto.builder()
        .url(findDetailNews.getUrl())
        .title(findDetailNews.getTitle())
        .contents(findDetailNews.getContents())
        .media(findDetailNews.getMediaName())
        .likeCount(findDetailNews.getLikeCount())
        .recommend(newsRecommendRepository
        .findByMemberRecommend_IdAndDetailNewsArchiveUrl(
        memberRecommendRepository.findByMember_Id(td.currentMemberId()).getId(), url)
        .orElse(NewsRecommend.getNoneRecommend()).getStatus())
        .postDateTime(findDetailNews.getPostDateTime())
        .modifyDateTime(findDetailNews.getModifyDateTime())
        .reporter(findDetailNews.getReporter())
        .isScrap(this.isExistScrapNews(url)).build())
        .result(RequestResult.builder()
        .resultCode("200")
        .resultMessage("상세 뉴스 조회 성공").build()).build();
    }

    @Override
    public ResponseThumbnailNewsDto findAllLiveNews(int page, int limit){
        // 페이징 조건
        PageRequest pageable = PageRequest
        .of(page - 1, limit, Sort.by("postDateTime").descending());

        // 페이징 쿼리
        Page<NewsArchive> pagingLiveNews = newsArchiveRepository.findAll(pageable);

        // 이번 페이징 조건에 맞게 페이징된
        // Entity Page를 DTO List로 변환하여 리턴
        return ResponseThumbnailNewsDto.builder()
        .thumbnailNewsList(newsArchiveToThumbnailDto(pagingLiveNews))
        .pageSource(ResponsePageSourceDto.builder()
        .page(page)
        .limit(limit)
        .totalPage(pagingLiveNews.getTotalPages()).build())
        .result(RequestResult.builder()
        .resultCode("200")
        .resultMessage("최신뉴스 조회 성공")
        .build()).build();
    }

    @Override
    public ResponseThumbnailNewsDto findAllSubscribeNews(int page, int limit) {

        // 전체 구독 목록 조회
        MemberSubscribeItem memberSubscribeItem = subscribeItemRepository
        .findSubscribeMediaByMemberId(td.currentMemberId()).orElseGet(MemberSubscribeItem::new);

        // 페이징 조건
        PageRequest pageable = PageRequest
        .of(page - 1, limit, Sort.by("post_Date_Time").descending());

        // 페이징 쿼리
        Page<NewsArchive> pagingSubscribeNews = newsArchiveRepository
        // 전체 구독 목록에서 언론사 부분만 추출?
        .findAllByFilters(memberSubscribeItem.getMemberSubscribeMediaItemList().stream()
        .map(s -> s.getMedia().getName()).toList(),

        // 전체 구독 목록에서 카테고리 부분만 추출?
        memberSubscribeItem.getSubscribeCategoryList().stream()
        .map(SubscribeCategory::getName).toList(),

        // 전체 구독 목록에서 키워드 부분만 추출?
        // 키워드는 하나의 문자열로 만들어서 찾아야함.
        String.join("|", memberSubscribeItem.getSubscribeKeywordList().stream()
        .map(SubscribeKeyword::getName).toList()), pageable);

        // 이번 페이징 조건에 맞게 페이징된
        // Entity Page를 DTO List로 변환하여 리턴
        return ResponseThumbnailNewsDto.builder()
        .thumbnailNewsList(newsArchiveToThumbnailDto(pagingSubscribeNews))
        .pageSource(ResponsePageSourceDto.builder()
        .page(page)
        .limit(limit)
        .totalPage(pagingSubscribeNews.getTotalPages()).build())
        .result(RequestResult.builder()
        .resultCode("200")
        .resultMessage("구독 뉴스 조회 성공")
        .build()).build();
    }

    private List<ThumbnailNewsDto> newsArchiveToThumbnailDto(Page<NewsArchive> newsArchivePage) {

        // Page객체의 너무 많은 정보들을 정리하고
        // 이번 페이지의 내용만 List로 변환함.
        return newsArchivePage.stream()
        .map(n -> ThumbnailNewsDto.builder()
        .url(n.getUrl())
        .media(n.getMediaName())
        .category(n.getCategory())
        .title(n.getTitle()).summary("")
        .imagePath(n.getImagePath())
        .postDateTime(n.getPostDateTime()).build()).toList();
    }

    // 이미 스크랩된 뉴스인지 확인.
    public boolean isExistScrapNews(String newsUrl) {
        Long memberId = td.currentMemberId();
        if (memberScrapNewsRepository.countByMemberId(memberId) > 0){
            MemberScrapNews memberScrapNews = memberScrapNewsRepository.findByMemberId(memberId).orElseThrow(() -> new IllegalStateException("Not found member"));
            List<MemberScrapNewsArchive> memberScrapNewsArchiveList = memberScrapNews.getMemberScrapNewsArchiveList();
            for (MemberScrapNewsArchive item : memberScrapNewsArchiveList) {
                if (item.getNewsArchive().getUrl().equals(newsUrl)){
                    return true;
                }
            }
        }
        return false;
    }
}
