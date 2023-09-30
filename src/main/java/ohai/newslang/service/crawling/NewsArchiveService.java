package ohai.newslang.service.crawling;

import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.entity.news.NewsArchive;
import ohai.newslang.repository.crawling.NewsArchiveRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NewsArchiveService {

    private final NewsArchiveRepository newsArchiveRepository;

    @Transactional
    public Long save(NewsArchive newsArchive){
        newsArchiveRepository.save(newsArchive);
        return newsArchive.getId();
    }

    public boolean isExistUrl(String url){
        return newsArchiveRepository.isExistNewsUrl(url);
    }

    public NewsArchive findOne(Long newsArchiveId){
        return newsArchiveRepository.findOne(newsArchiveId);
    }

    public NewsArchive findByUrl(String url){
        return newsArchiveRepository.findByUrl(url);
    }

    public List<NewsArchive> findByNameList(List<String> mediaNameList, List<String> categoryNameList, List<String> keywordNameList) {
        List<NewsArchive> newsArchiveList = newsArchiveRepository.findAllWithNameList(mediaNameList, categoryNameList);
//            List<NewsArchive> newNewsArchiveList = new ArrayList<>();
//            for (String keyword: keywordNameList) {
//                for (NewsArchive item: newsArchiveList) {
//                    if (item.getNews().getContents().contains(keyword)){
//                        newNewsArchiveList.add(item);
//                    }
//                }
//            }
        newsArchiveList = newsArchiveList.stream()
                .filter(item -> keywordNameList.stream()
                        .anyMatch(keyword -> item.getNews().getContents().contains(keyword)))
                .collect(Collectors.toList());
        return newsArchiveList;
    }
}
