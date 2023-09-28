package ohai.newslang.service.crawling;

import ohai.newslang.domain.ThumbnailNews;
import ohai.newslang.domain.subscribe.reference.Media;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MediaCrawlingServiceImpl implements CrawlingService{
    @Override
    public List<ThumbnailNews> crawlingThumbnailNews(String url) {
        return null;
    }

    @Override
    public List<Media> crawlingMedia(String url) {
        List<Media> mediaList = new ArrayList<>();
        Document doc = null;
        Connection conn = Jsoup.connect(url);
        try {
            doc = conn.get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Element tbody = doc.select("tbody").get(1);
        Elements trElements = tbody.select("tr");
        for (Element tr : trElements) {
            String mediaGroup = tr.select("th").html();
            Element groupListElements = tr.getElementsByClass("group_list").get(0);
            Elements liElements = groupListElements.select("li");
            for (Element li : liElements) {
                String mediaName = li.select("a").html();
                String mediaParam = li.select("a").attr("href");
                String mediaParamId = "";
                try {
                    mediaParamId = UriComponentsBuilder.fromUriString(mediaParam).build().getQueryParams().get("oid").get(0);
                }catch (Exception ex){
                    continue;
                }
                Media m = new Media();
                m.setName(mediaName);
                m.setMediaGroup(mediaGroup);
                m.setParameterId(mediaParamId);
                mediaList.add(m);
                System.out.println("a");
            }
        }

        return mediaList;
    }

//    private List<Media> getThumbnailNewsList(Elements root){
//        List<Media> thumbnailNewsList = new ArrayList<>();
//        for (Element item: root) {
//            Elements lis = item.select("li");
//            for (Element innerItem: lis) {
//                Elements el = innerItem.select("dt").get(1).select("a");
//                String title = el.html();
//                String link = el.attr("href");
//                String imagePath = innerItem.select("img").attr("src");
//                String summary = innerItem.getElementsByClass("lede").html();
//                String mediaName = innerItem.getElementsByClass("writing").html();
//                thumbnailNewsList.add(ThumbnailNews.builder().link(link).title(title).summary(summary).imagePath(imagePath).mediaName(mediaName).build());
//            }
//        }
//        return thumbnailNewsList;
//    }

}