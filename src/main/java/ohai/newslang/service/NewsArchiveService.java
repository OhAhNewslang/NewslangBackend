package ohai.newslang.service;

import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.NewsArchive;
import ohai.newslang.repository.NewsArchiveRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public NewsArchive findOne(Long newsArchiveId){
        return newsArchiveRepository.findOne(newsArchiveId);
    }

    public NewsArchive findByUrl(String url){
        return newsArchiveRepository.findByUrl(url);
    }
}
