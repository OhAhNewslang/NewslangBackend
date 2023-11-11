package ohai.newslang.service.news;

import lombok.RequiredArgsConstructor;
import ohai.newslang.configuration.jwt.TokenDecoder;
import ohai.newslang.domain.dto.news.*;
import ohai.newslang.domain.dto.page.ResponsePageSourceDto;
import ohai.newslang.domain.dto.request.RequestResult;
import ohai.newslang.domain.entity.news.NewsArchive;
import ohai.newslang.domain.entity.recommend.NewsRecommend;
import ohai.newslang.domain.entity.scrap.MemberScrapNews;
import ohai.newslang.domain.entity.scrap.MemberScrapNewsArchive;
import ohai.newslang.domain.entity.subscribe.MemberSubscribeItem;
import ohai.newslang.domain.entity.subscribe.SubscribeCategory;
import ohai.newslang.domain.entity.subscribe.SubscribeKeyword;
import ohai.newslang.domain.enumulate.SubscribeStatus;
import ohai.newslang.domain.vo.MemberNewsStatus;
import ohai.newslang.repository.news.NewsArchiveRepository;
import ohai.newslang.repository.recommand.MemberRecommendRepository;
import ohai.newslang.repository.recommand.NewsRecommendRepository;
import ohai.newslang.repository.scrap.MemberScrapNewsRepository;
import ohai.newslang.repository.subscribe.MemberSubscribeItemRepository;
import ohai.newslang.repository.subscribe.subscribeReference.CategoryRepository;
import ohai.newslang.repository.subscribe.subscribeReference.MediaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NewsArchiveServiceImpl implements NewsArchiveService{

    private final NewsArchiveRepository newsArchiveRepository;
    private final MemberSubscribeItemRepository subscribeItemRepository;
    private final NewsRecommendRepository newsRecommendRepository;
    private final MemberRecommendRepository memberRecommendRepository;
    private final MemberScrapNewsRepository memberScrapNewsRepository;
    private final MediaRepository mediaRepository;
    private final CategoryRepository categoryRepository;
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
        .article(findDetailNews.getArticle())
        .media(findDetailNews.getMediaName())
        .likeCount(findDetailNews.getLikeCount())
        .postDateTime(findDetailNews.getPostDateTime())
        .modifyDateTime(findDetailNews.getModifyDateTime())
        .reporter(findDetailNews.getReporter())
        .build())
        .result(RequestResult.builder()
        .resultCode("200")
        .resultMessage("상세 뉴스 조회 성공").build()).build();
    }

    @Override
    public MemberNewsStatusDto findNewsStatusByUrl(String url){
        NewsArchive findDetailNews = newsArchiveRepository.findNewsArchiveByUrl(url);
        return MemberNewsStatusDto.builder()
                .memberNewsStatus(MemberNewsStatus.builder()
                        .recommend(newsRecommendRepository
                                .findByMemberRecommend_IdAndDetailNewsArchiveUrl(memberRecommendRepository
                                        .findByMember_Id(td.currentMemberId()).getId(), url)
                                .orElse(NewsRecommend.getNoneRecommend()).getStatus())
                        .isScrap(this.isExistScrapNews(url))
                        .build())
                .result(RequestResult.builder()
                        .resultCode("200")
                        .resultMessage("회원 뉴스 상태 조회 성공")
                        .build())
                .build();
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
                .findByMemberId(td.currentMemberId()).orElseGet(MemberSubscribeItem::new);

        // 구독 상태
        SubscribeStatus mediaSubscribeStatus = memberSubscribeItem.getMediaSubscribeStatus();
        SubscribeStatus categorySubscribeStatus = memberSubscribeItem.getCategorySubscribeStatus();
        SubscribeStatus keywordSubscribeStatus = memberSubscribeItem.getKeywordSubscribeStatus();

        // 언론사, 카테고리 구독 리스트 취득
        Set<String> mediaSet = memberSubscribeItem.getMemberSubscribeMediaItemList().stream().map(s -> s.getMedia().getName()).collect(Collectors.toSet());
        Set<String> categorySet = memberSubscribeItem.getSubscribeCategoryList().stream().map(SubscribeCategory::getName).collect(Collectors.toSet());

        // 페이징 조건
        PageRequest pageable = PageRequest
                .of(page - 1, limit, Sort.by("post_Date_Time").descending());

        // 전체 구독인 경우 모든 미디어 추가
        if (mediaSubscribeStatus == SubscribeStatus.ALL) {
            List<String> mediaList = mediaRepository.findAll().stream().map(m -> m.getName()).toList();
            mediaSet.addAll(mediaList);
        }

        // 전체 구독인 경우 모든 카테고리 추가
        if (categorySubscribeStatus == SubscribeStatus.ALL) {
            List<String> categoryList = categoryRepository.findAll().stream().map(m -> m.getName()).toList();
            categorySet.addAll(categoryList);
        }

        Page<NewsArchive> pagingSubscribeNews;
        if (keywordSubscribeStatus == SubscribeStatus.ALL){
            // 키워드 제외의 경우
            pagingSubscribeNews = newsArchiveRepository
                    .findAllByFiltersIgnoreKeywords(mediaSet.stream().toList(),
                            categorySet.stream().toList(),
                            pageable);
        }else{
            // 키워드 포함의 경우
            String keywords = String.join("|", memberSubscribeItem.getSubscribeKeywordList().stream().map(SubscribeKeyword::getName).toList());
            pagingSubscribeNews = newsArchiveRepository
                    .findAllByFilters(mediaSet.stream().toList(),
                            categorySet.stream().toList(),
                            // REGEXP 조건을 위한 하나의 문자열로 만듬 (| : or 조건)
                            keywords, pageable);
        }

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

    @Override
    public KeywordNewsDto findAllKeywordNews(String keyword) {

        List<NewsArchive> pagingSubscribeNews = newsArchiveRepository
                .findTop3ByKeyword(keyword);

        // 이번 페이징 조건에 맞게 페이징된
        // Entity Page를 DTO List로 변환하여 리턴
        return KeywordNewsDto.builder()
                .thumbnailNewsList(newsArchiveToThumbnailDto(pagingSubscribeNews))
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

    private List<ThumbnailNewsDto> newsArchiveToThumbnailDto(List<NewsArchive> newsArchivePage) {

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
