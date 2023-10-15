package ohai.newslang.service.scrap;

import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.entity.member.Member;
import ohai.newslang.domain.entity.news.NewsArchive;
import ohai.newslang.domain.entity.scrap.MemberScrapNews;
import ohai.newslang.domain.entity.scrap.MemberScrapNewsArchive;
import ohai.newslang.repository.crawling.NewsArchiveRepository;
import ohai.newslang.repository.member.MemberRepository;
import ohai.newslang.repository.scrap.MemberScrapNewsArchiveRepository;
import ohai.newslang.repository.scrap.MemberScrapNewsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberScrapNewsServiceImpl implements MemberScrapNewsService {

    private final MemberScrapNewsRepository memberScrapNewsRepository;
    private final MemberScrapNewsArchiveRepository memberScrapNewsArchiveRepository;
    private final MemberRepository memberRepository;
    private final NewsArchiveRepository newsArchiveRepository;

    @Override
    public Page<MemberScrapNewsArchive> getNewsArchiveList(Long memberId, int page, int limit) {
        if (memberScrapNewsRepository.countByMemberId(memberId) > 0){
            MemberScrapNews memberScrapNews = memberScrapNewsRepository.findByMemberId(memberId);
            Long memberScrapNewsId = memberScrapNews.getId();
            PageRequest pageable = PageRequest.of(page - 1, limit, Sort.by("scrapDateTime").descending());
            Page<MemberScrapNewsArchive> memberScrapNewsArchiveList = memberScrapNewsArchiveRepository.findByMemberScrapNewsId(memberScrapNewsId, pageable);
            return memberScrapNewsArchiveList;
        }
        return null;
    }

    @Override
    public boolean isExistScrapNews(Long memberId, String newsUrl) {
        if (memberScrapNewsRepository.countByMemberId(memberId) > 0){
            MemberScrapNews memberScrapNews = memberScrapNewsRepository.findByMemberId(memberId);
            List<MemberScrapNewsArchive> memberScrapNewsArchiveList = memberScrapNews.getMemberScrapNewsArchiveList();
            boolean isAlreadyUrl = false;
            for (MemberScrapNewsArchive item : memberScrapNewsArchiveList) {
                if (item.getNewsArchive().getUrl() == newsUrl){
                    isAlreadyUrl = true;
                    break;
                }
            }
            return isAlreadyUrl;
        }
        return false;
    }

    @Override
    @Transactional
    public Long addScrapNews(Long memberId, Long newsArchiveId) {
        MemberScrapNews memberScrapNews = null;
        Optional<NewsArchive> newsArchive = newsArchiveRepository.findById(newsArchiveId);
        if (memberScrapNewsRepository.countByMemberId(memberId) > 0){
            memberScrapNews = memberScrapNewsRepository.findByMemberId(memberId);
            Long memberScrapNewsId = memberScrapNews.getId();
            List<MemberScrapNewsArchive> memberScrapNewsArchiveList = memberScrapNewsArchiveRepository.findByMemberScrapNewsId(memberScrapNewsId);
            String newsUrl = newsArchive.get().getUrl();
            boolean isAlreadyUrl = false;
            for (MemberScrapNewsArchive item : memberScrapNewsArchiveList) {
                if (item.getNewsArchive().getUrl() == newsUrl){
                    isAlreadyUrl = true;
                    break;
                }
            }
            if (isAlreadyUrl)
            {
                return memberScrapNewsId;
            }
        }
        Member member = memberRepository.findById(memberId).get();
        // findOne -> findById + get() 메서드(Optional개봉)
        memberScrapNews = MemberScrapNews.newMemberScrapNews(memberScrapNews, member, newsArchive.get());
        memberScrapNewsRepository.save(memberScrapNews);
        return memberScrapNews.getId();
    }

    @Override
    @Transactional
    public void removeScrapNews(Long memberId, String url) {
        if (memberScrapNewsRepository.countByMemberId(memberId) > 0){
            MemberScrapNews memberScrapNews = memberScrapNewsRepository.findByMemberId(memberId);
            Long memberScrapNewsId = memberScrapNews.getId();
            List<MemberScrapNewsArchive> memberScrapNewsArchiveList = memberScrapNewsArchiveRepository.findByMemberScrapNewsId(memberScrapNewsId);
            List<String> urlList = memberScrapNewsArchiveList.stream()
                    .map(n -> n.getNewsArchive().getUrl())
                    .collect(Collectors.toList());
            memberScrapNews.removeMemberScrapNewsArchive(urlList);
        }
    }
}
