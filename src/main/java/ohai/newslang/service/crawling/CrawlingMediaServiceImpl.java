package ohai.newslang.service.crawling;

import ohai.newslang.domain.dto.ThumbnailNews;
import ohai.newslang.domain.entity.subscribe.subscribeReference.Media;
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
public class CrawlingMediaServiceImpl implements CrawlingMediaService{

    @Override
    public List<Media> getMediaList(String url) {
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
            }
        }

        return mediaList;
    }
}