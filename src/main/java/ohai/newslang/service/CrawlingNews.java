package ohai.newslang.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohai.newslang.domain.News;
import ohai.newslang.domain.entity.news.NewsArchive;
import ohai.newslang.domain.entity.subscribe.subscribeReference.Media;
import ohai.newslang.service.crawling.CrawlingNewsService;
import ohai.newslang.service.news.NewsArchiveService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CrawlingNews {

    private final NewsArchiveService newsArchiveService;
    private final CrawlingNewsService crawlingNewsService;

    public void run(String Date, List<Media> mediaList) {
        int addDay = 0;
        while (true) {
            // 시작 날짜부터 현재날짜까지
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyyMMdd");
            LocalDate startDate = LocalDate.parse(Date, dateFormat);
            if (!LocalDate.now().isBefore(startDate.plusDays(addDay))) {
                startDate = startDate.plusDays(addDay);
            }
            String crawlingDate = startDate.format(dateFormat);
            mediaList.forEach(m -> {
                String oId = m.getOId();
                int pageNo = 1;
                while (true) {
                    String category = m.getMediaGroup();
                    List<News> newsList = crawlingNewsService.getNewsList(oId, crawlingDate, pageNo);

                    // 페이지 번호가 마지막일 때, 마지막 페이지에 데이터가 있을 경우 이전 뉴스 데이터가 계속 표출되었음 (네이버 뉴스 페이지 특성인 것 같음)
                    // 다음의 경우를 마지막 페이지라고 생각하여 반복문 종료 조건
                    // 1. 마지막 페이지에 뉴스가 없을 경우
                    // 2. 마지막 페이지의 뉴스 중 이미 등록된 뉴스가 있을 경우

                    if (newsList.size() < 1) break;

                    List<String> urlList = newsList.stream()
                            .map(t -> t.getUrl())
                            .collect(Collectors.toList());
                    List<String> alreadyExistUrl = newsArchiveService.isAlreadyExistUrl(urlList);

                    if (alreadyExistUrl.size() > 0)
                        break;
                    if (newsList.size() == alreadyExistUrl.size())
                        break;

                    List<NewsArchive> newsArchiveList = new ArrayList<>();
                    newsList.forEach(t -> {
                        if (!alreadyExistUrl.contains(t.getUrl())) {
                            newsArchiveList.add(NewsArchive.builder()
                                    .url(t.getUrl())
                                    .mediaName(t.getMedia())
                                    .category(category)
                                    .title(t.getTitle())
                                    .contents(t.getContents())
                                    .imagePath(t.getImagePath())
                                    .countLike(0L)
                                    .postDateTime(t.getPostDateTime())
                                    .modifyDateTime(t.getModifyDateTime())
                                    .reporter(t.getReporter())
                                    .build());
                        }
                    });
                    if (newsArchiveList.size() > 0)
                        newsArchiveService.saveAll(newsArchiveList);
                    pageNo++;
                }
            });
            addDay++;
        }
    }
}
