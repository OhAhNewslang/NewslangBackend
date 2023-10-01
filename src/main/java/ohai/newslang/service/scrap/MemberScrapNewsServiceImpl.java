package ohai.newslang.service.scrap;

import ohai.newslang.domain.entity.news.NewsArchive;
import ohai.newslang.domain.entity.scrap.MemberScrapNewsArchive;

import java.util.List;

public interface MemberScrapNewsServiceImpl {

    List<MemberScrapNewsArchive> findNewsArchiveList(Long memberId);
    Long addNewsArchive(Long memberId, NewsArchive newsArchive);
    void removeNewsArchive(Long memberId, String url);
}
