package ohai.newslang.service.scrap;

import ohai.newslang.domain.entity.scrap.MemberScrapNewsArchive;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MemberScrapNewsService {

    Page<MemberScrapNewsArchive> getNewsArchiveList(Long memberId, int page, int limit);
    boolean isExistScrapNews(Long memberId, String newsUrl);
    Long addScrapNews(Long memberId, Long newsArchiveId);
    void removeScrapNews(Long memberId, String url);
}
