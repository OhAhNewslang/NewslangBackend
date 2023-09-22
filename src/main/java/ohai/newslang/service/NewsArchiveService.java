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
        /*
        keyword 정보가 없을 때 : media 와 category 해당되는 정보 보여주기
         */
        List<NewsArchive> newsArchiveList = newsArchiveRepository.findAllWithNameList(mediaNameList, categoryNameList);
        if (keywordNameList.size() > 0) {
            newsArchiveList = newsArchiveList.stream()
                    .filter(item -> keywordNameList.stream()
                            .anyMatch(keyword -> item.getNews().getContents().contains(keyword)))
                    .collect(Collectors.toList());
        }
        return newsArchiveList;
    }
}
