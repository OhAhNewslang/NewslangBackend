package ohai.newslang.service;

import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.NewsArchive;
import ohai.newslang.repository.NewsArchiveRepository;
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
    public void save(NewsArchive newsArchive){
        newsArchiveRepository.save(newsArchive);
    }

    public NewsArchive findOne(Long newsArchiveId){
        return newsArchiveRepository.findOne(newsArchiveId);
    }

    public NewsArchive findByUrl(String url){
        return newsArchiveRepository.findByUrl(url);
    }

    public List<NewsArchive> findByNameList(List<String> mediaNameList, List<String> categoryNameList, List<String> keywordNameList) {
        List<NewsArchive> newsArchiveList = newsArchiveRepository.findAllWithNameList(mediaNameList, categoryNameList);
        newsArchiveList = newsArchiveList.stream()
                .filter(item -> keywordNameList.stream()
                        .anyMatch(keyword -> item.getNews().getContents().contains(keyword)))
                .collect(Collectors.toList());
        return newsArchiveList;
    }
}
