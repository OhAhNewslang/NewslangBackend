package ohai.newslang.service.crawling;

import lombok.extern.slf4j.Slf4j;
import ohai.newslang.domain.News;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CrawlingNewsServiceImpl implements CrawlingNewsService {

    @Override
    public List<News> getNewsList(String oId, String date, int page) throws InterruptedException{
        List<News> newsList = new ArrayList<>();
        Document doc = getDocument("https://news.naver.com/main/list.naver?mode=LPOD&mid=sec&oid=" + oId + "&date=" + date + "&page=" + page,
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/118.0.0.0 Safari/537.36",
                40000);
        Elements type06_headline = doc.getElementsByClass("type06_headline");
        Elements type06 = doc.getElementsByClass("type06");
        if (type06_headline != null && type06_headline.size() > 0)
            newsList.addAll(parsingScriptToThumbnailNews(type06_headline, oId));
        if (type06 != null && type06.size() > 0)
            newsList.addAll(parsingScriptToThumbnailNews(type06, oId));
        return newsList;
    }

    private List<News> parsingScriptToThumbnailNews(Elements root, String oId){
        List<News> newsList = new ArrayList<>();
        try {
            for (Element item : root) {
                Elements lis = item.select("li");
                for (Element innerItem : lis) {
                    try {
                        Elements photo = innerItem.getElementsByClass("photo");
                        int dtIdx = 0;
                        String imagePath = "";
                        if (photo != null && photo.size() > 0) {
                            imagePath = photo.select("img").attr("src");
                            dtIdx = 1;
                        }
                        Elements dt2 = innerItem.select("dt").get(dtIdx).select("a");
                        String title = dt2.html();
                        String link = dt2.select("a").attr("href");
                        Elements dd = innerItem.select("dd");
                        String summary = dd.select("span").get(0).html();
                        String mediaName = dd.select("span").get(1).html();

                        String contents = "";
                        String reporter = "";
                        LocalDateTime postDateTime = null;
                        LocalDateTime modifyDateTime = null;

                        Document doc = getDocument(link,
                                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/118.0.0.0 Safari/537.36",
                                40000);
                        Elements article = doc.getElementsByClass("go_trans _article_content");
                        contents += article.toString();
                        postDateTime = getDateTime(doc, "media_end_head_info_datestamp_time _ARTICLE_DATE_TIME", "data-date-time");
                        modifyDateTime = getDateTime(doc, "media_end_head_info_datestamp_time _ARTICLE_MODIFY_DATE_TIME", "data-modify-date-time");
                        if (modifyDateTime == null) modifyDateTime = postDateTime;
                        Elements elementsRepo = doc.getElementsByClass("byline_p");
                        if (elementsRepo != null) {
                            Elements elementsRepoSpan = elementsRepo.select("span");
                            if (elementsRepoSpan != null) {
                                for (int i = 0; i < elementsRepoSpan.size(); i++) {
                                    if (reporter != "") reporter += "\r\n";
                                    reporter += elementsRepoSpan.get(i).html();
                                }
                            }
                        }
                        newsList.add(News.builder().url(link).title(title).summary(summary).contents(contents).imagePath(imagePath).media(mediaName).oId(oId).postDateTime(postDateTime).modifyDateTime(modifyDateTime).reporter(reporter).build());
                    }catch (RuntimeException e) {
                        throw new RuntimeException(e);
                    }
                    catch (Exception ex){
                        log.error("Parsing Error : " + ex.getMessage());
                    }
                }
            }
        }catch (RuntimeException e){
            throw new RuntimeException(e);
        }
        return newsList;
    }

    private Document getDocument(String url, String userAgent, int millis) throws InterruptedException {
        // 페이지 크롤링 담당
        // 페이지 크롤링 거부시 재시도 반복
        Connection conn = Jsoup
                .connect(url)
                .userAgent(userAgent)
                .timeout(millis);
        Document doc = null;
        int retryCount = 0;
        while (doc == null){
            try {
                Connection.Response resp = conn.execute();
                doc = conn.get();
            } catch (IOException ex) {
                // 뉴스 상세 내용 크롤링에서 문제 발생
                // 재시도 반복
                log.error("Not response... Retry[" + retryCount + "]- " + url);
                retryCount++;
                Thread.sleep(1000);
            }
        }
        return doc;
    }

    private LocalDateTime getDateTime(Document document, String className, String attributeKey){
        LocalDateTime returnDateTime = null;
        Elements elements = document.getElementsByClass(className);
        if (elements != null && elements.size() > 0) {
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String strTime = elements.attr(attributeKey);
            if (strTime != "")
                returnDateTime = LocalDateTime.parse(strTime, dateFormat);
        }
        return returnDateTime;
    }
}