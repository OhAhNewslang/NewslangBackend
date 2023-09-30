package ohai.newslang.service.crawling;

import ohai.newslang.domain.dto.ThumbnailNews;
import ohai.newslang.domain.entity.subscribe.subscribeReference.Media;
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
                    thumbnailNewsList.add(ThumbnailNews.builder().link(link).title(title).summary(summary).imagePath(imagePath).mediaName(mediaName).build());
                }
            }
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        return thumbnailNewsList;
    }
}