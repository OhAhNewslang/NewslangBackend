package ohai.newslang.service.crawling;

import ohai.newslang.domain.DetailNews;
import ohai.newslang.domain.ThumbnailNews;
import ohai.newslang.domain.entity.subscribe.subscribeReference.Media;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CrawlingNewsServiceImpl implements CrawlingNewsService{

    @Override
    public List<ThumbnailNews> getThumbnailNewsList(List<Media> mediaList, String date, int lastPageNo)  {
        List<ThumbnailNews> thumbnailNewsList = new ArrayList<>();
        HashSet<String> alreadyUrlSet = new HashSet<>();
        mediaList.forEach(m ->{
            String mediaId = m.getParameterId();
            String category = m.getMediaGroup();
            int pageNo = 1;
            while (lastPageNo >= pageNo) {
                String url = "https://news.naver.com/main/list.naver?mode=LPOD&mid=sec&oid=" + mediaId + "&date=" + date + "&page=" + pageNo;
                Document doc = null;
                Connection conn = Jsoup.connect(url);
                try {
                    doc = conn.get();
                } catch (IOException e) {
                    throw new RuntimeException("Not exist contents - " + e.getMessage());
                }
                if (doc != null) {
                    Elements type06_headline = doc.getElementsByClass("type06_headline");
                    Elements type06 = doc.getElementsByClass("type06");
                    parsingScriptToThumbnailNews(type06_headline, category);

                    List<ThumbnailNews> newsList = parsingScriptToThumbnailNews(type06_headline, category);
                    newsList.addAll(parsingScriptToThumbnailNews(type06, category));

                    if (newsList.size() < 1) break;
                    List<String> urlList = newsList.stream()
                            .map(t -> t.getLink())
                            .collect(Collectors.toList());
                    if (alreadyUrlSet.containsAll(urlList)) break;

                    thumbnailNewsList.addAll(newsList);
                }
                pageNo++;
            }
        });
        return thumbnailNewsList;
    }

    @Override
    public DetailNews getDetailNewsList(String url, String media){
        Document doc = null;
        Connection conn = Jsoup.connect(url);
        try {
            doc = conn.get();
        } catch (IOException e) {
            throw new RuntimeException("Not exist contents - " + e.getMessage());
        }
        if (doc != null) {
            String contents = "";

            LocalDateTime postDateTime = getDateTime(doc, "media_end_head_info_datestamp_time _ARTICLE_DATE_TIME", "data-date-time");
            LocalDateTime modifyDateTime= getDateTime(doc, "media_end_head_info_datestamp_time _ARTICLE_MODIFY_DATE_TIME", "data-modify-date-time");

            Elements title = doc.getElementsByClass("media_end_head_headline");
            String newsTitle = title.select("span").get(0).html();
            Elements body = doc.getElementsByClass("newsct_body");
            for (Element item : body){
                Elements article = item.getElementsByClass("newsct_article _article_body");
                contents += article.toString();
                Elements copyright = item.getElementsByClass("copyright");
                contents += copyright.toString();
            }
            return DetailNews.builder().link(url).title(newsTitle).contents(contents).media(media).postDateTime(postDateTime).modifyDateTime(modifyDateTime).build();
        }
        return null;
    }

    private List<ThumbnailNews> parsingScriptToThumbnailNews(Elements root, String category){
        List<ThumbnailNews> thumbnailNewsList = new ArrayList<>();
        try {
            for (Element item : root) {
                Elements lis = item.select("li");
                for (Element innerItem : lis) {
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
                    LocalDateTime postDateTime = null;
                    Document doc = null;
                    Connection conn = Jsoup.connect(link);
                    try {
                        doc = conn.get();
                    } catch (IOException e) {
                        throw new RuntimeException("Not exist contents - " + e.getMessage());
                    }
                    if (doc != null) {
                        Elements article = item.getElementsByClass("go_trans _article_content");
                        contents += article.toString();
                        postDateTime = getDateTime(doc, "media_end_head_info_datestamp_time _ARTICLE_DATE_TIME", "data-date-time");
                    }
                    thumbnailNewsList.add(ThumbnailNews.builder().link(link).title(title).summary(summary).contents(contents).imagePath(imagePath).media(mediaName).category(category).postDateTime(postDateTime).build());
                }
            }
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        return thumbnailNewsList;
    }

    private LocalDateTime getDateTime(Document document, String className, String attributeKey){
        LocalDateTime returnDateTime = null;
        Elements elements = document.getElementsByClass(className);
        if (elements != null && elements.size() > 0) {
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String strTime = elements.attr(attributeKey);
            returnDateTime = LocalDateTime.parse(strTime, dateFormat);
        }
        return returnDateTime;
    }
}