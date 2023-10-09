package ohai.newslang.service.scrap;

import ohai.newslang.domain.entity.scrap.MemberScrapNewsArchive;

import java.util.List;

public interface MemberScrapNewsService {

    List<MemberScrapNewsArchive> getNewsArchiveList(Long memberId);
    Long addNewsArchive(Long memberId, Long scrapNewsArchiveId);
    void removeNewsArchive(Long memberId, String url);
}
