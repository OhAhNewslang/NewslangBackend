package ohai.newslang.service.crawling;

import ohai.newslang.domain.ThumbnailNews;
import ohai.newslang.domain.subscribe.item.Media;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ThumbnailNewsCrawlingServiceImpl implements CrawlingService{

    @Override
    public List<ThumbnailNews> crawlingThumbnailNews(String url) {
        List<ThumbnailNews> thumbnailNewsList = new ArrayList<>();
        Document doc = null;
        Connection conn = Jsoup.connect(url);
        try {
            doc = conn.get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Elements type06_headline = doc.getElementsByClass("type06_headline");
        Elements type06 = doc.getElementsByClass("type06");
        thumbnailNewsList.addAll(getThumbnailNewsList(type06_headline));
        thumbnailNewsList.addAll(getThumbnailNewsList(type06));

        return thumbnailNewsList;
    }

    @Override
    public List<Media> crawlingMedia(String url) {
        return null;
    }

    private List<ThumbnailNews> getThumbnailNewsList(Elements root){
        List<ThumbnailNews> thumbnailNewsList = new ArrayList<>();
        for (Element item: root) {
            Elements lis = item.select("li");
            for (Element innerItem: lis) {
                Elements el = innerItem.select("dt").get(1).select("a");
                String title = el.html();
                String link = el.attr("href");
                String imagePath = innerItem.select("img").attr("src");
                String summary = innerItem.getElementsByClass("lede").html();
                String mediaName = innerItem.getElementsByClass("writing").html();
                thumbnailNewsList.add(ThumbnailNews.builder().link(link).title(title).summary(summary).imagePath(imagePath).mediaName(mediaName).build());
            }
        }
        return thumbnailNewsList;
    }
}
