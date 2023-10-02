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
public class NewsArchiveServiceImpl implements NewsArchiveService{

    private final NewsArchiveRepository newsArchiveRepository;

    @Override
    @Transactional
    public Long save(NewsArchive newsArchive){
        newsArchiveRepository.save(newsArchive);
        return newsArchive.getId();
    }

    @Override
    @Transactional
    public void saveAll(List<NewsArchive> newsArchiveList){
        newsArchiveRepository.saveAll(newsArchiveList);
    }

    @Override
    public boolean isExistUrl(String url){
        return newsArchiveRepository.countByUrl(url) > 0;
    }

    @Override
    public List<String> isAlreadyExistUrl(List<String> urlList){
        return newsArchiveRepository.alreadyExistByUrl(urlList);
    }

    @Override
    public NewsArchive findByUrl(String url){
        return newsArchiveRepository.findByUrl(url);
    }

    @Override
    public List<NewsArchive> getNewsArchiveList(List<String> mediaNameList, List<String> categoryNameList, List<String> keywordNameList) {
        List<NewsArchive> newsArchiveList = newsArchiveRepository.findByMediaNamesAndCategoryNames(mediaNameList, categoryNameList);
        newsArchiveList = newsArchiveList.stream()
                .filter(item -> keywordNameList.stream()
                        .anyMatch(keyword -> item.getNews().getContents().contains(keyword)))
                .collect(Collectors.toList());
        return newsArchiveList;
    }
}
