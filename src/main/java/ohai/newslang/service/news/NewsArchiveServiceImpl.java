package ohai.newslang.service.news;

import lombok.RequiredArgsConstructor;
import ohai.newslang.configuration.jwt.TokenDecoder;
import ohai.newslang.domain.dto.news.ResponseThumbnailNewsDto;
import ohai.newslang.domain.dto.news.ResultDetailNewsDto;
import ohai.newslang.domain.dto.news.ThumbnailNewsDto;
import ohai.newslang.domain.dto.page.ResponsePageSourceDto;
import ohai.newslang.domain.dto.request.RequestResult;
import ohai.newslang.domain.entity.news.NewsArchive;
import ohai.newslang.domain.entity.subscribe.MemberSubscribeItem;
import ohai.newslang.domain.entity.subscribe.SubscribeCategory;
import ohai.newslang.domain.entity.subscribe.SubscribeKeyword;
import ohai.newslang.repository.news.NewsArchiveRepository;
import ohai.newslang.repository.subscribe.MemberSubscribeItemRepository;
import ohai.newslang.repository.subscribe.MemberSubscribeMediaItemRepository;
import ohai.newslang.repository.subscribe.SubscribeCategoryRepository;
import ohai.newslang.repository.subscribe.SubscribeKeywordRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NewsArchiveServiceImpl implements NewsArchiveService{

    private final NewsArchiveRepository newsArchiveRepository;
    private final MemberSubscribeItemRepository subscribeItemRepository;
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
        .url(findDetailNews.getUrl())
        .title(findDetailNews.getTitle())
        .contents(findDetailNews.getContents())
        .media(findDetailNews.getMediaName())
        .likeCount(findDetailNews.getLikeCount())
        .postDateTime(findDetailNews.getPostDateTime())
        .modifyDateTime(findDetailNews.getModifyDateTime())
        .reporter(findDetailNews.getReporter())
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
        // 제대로 작동 안하는 것으로 보임.
        MemberSubscribeItem memberSubscribeItem = subscribeItemRepository
        .findByMemberId(td.currentUserId()).orElseGet(MemberSubscribeItem::new);

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
}
