package ohai.newslang.service.crawling;

import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.entity.news.NewsArchive;
import ohai.newslang.repository.crawling.NewsArchiveRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public Page<NewsArchive> findAll(int page, int limit){
        PageRequest pageable = PageRequest.of(page - 1, limit, Sort.by("postDateTime").descending()); // jpql 사용
        Page<NewsArchive> newsArchivePages = newsArchiveRepository.findAll(pageable);
        return newsArchivePages;
    }

    @Override
    public Page<NewsArchive> getNewsArchiveList(List<String> mediaNameList, List<String> categoryNameList, List<String> keywordNameList, int page, int limit) {
        PageRequest pageable = PageRequest.of(page - 1, limit, Sort.by("post_Date_Time").descending()); // native query 사용
        String keywords = String.join("|", keywordNameList);
        return newsArchiveRepository.findByFilters(mediaNameList, categoryNameList, keywords, pageable);
    }
}
