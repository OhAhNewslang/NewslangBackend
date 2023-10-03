package ohai.newslang.service.scrap;

import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.entity.member.Member;
import ohai.newslang.domain.entity.news.News;
import ohai.newslang.domain.entity.news.NewsArchive;
import ohai.newslang.domain.entity.scrap.MemberScrapNews;
import ohai.newslang.domain.entity.scrap.MemberScrapNewsArchive;
import ohai.newslang.repository.member.MemberRepository;
import ohai.newslang.repository.scrap.MemberScrapNewsArchiveRepository;
import ohai.newslang.repository.scrap.MemberScrapNewsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberScrapNewsServiceImpl implements MemberScrapNewsService {

    private final MemberScrapNewsRepository memberScrapNewsRepository;
    private final MemberScrapNewsArchiveRepository memberScrapNewsArchiveRepository;
    private final MemberRepository memberRepository;

    @Override
    public List<MemberScrapNewsArchive> getNewsArchiveList(Long memberId) {
        if (memberScrapNewsRepository.countByMemberId(memberId) > 0){
            MemberScrapNews memberScrapNews = memberScrapNewsRepository.findByMemberId(memberId);
            Long memberScrapNewsId = memberScrapNews.getId();
            List<MemberScrapNewsArchive> memberScrapNewsArchiveList = memberScrapNewsArchiveRepository.findByMemberScrapNewsId(memberScrapNewsId);
            return memberScrapNewsArchiveList;
        }
        return null;
    }

    @Override
    @Transactional
    public Long addNewsArchive(Long memberId, NewsArchive newsArchive) {
        MemberScrapNews memberScrapNews = null;
        if (memberScrapNewsRepository.countByMemberId(memberId) > 0){
            memberScrapNews = memberScrapNewsRepository.findByMemberId(memberId);
            Long memberScrapNewsId = memberScrapNews.getId();
            List<MemberScrapNewsArchive> memberScrapNewsArchiveList = memberScrapNewsArchiveRepository.findByMemberScrapNewsId(memberScrapNewsId);
            String newUrl = newsArchive.getNews().getUrl();
            boolean isAlreadyUrl = false;
            for (MemberScrapNewsArchive item : memberScrapNewsArchiveList) {
                if (item.getNewsArchive().getNews().getUrl() == newUrl){
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
        memberScrapNews = MemberScrapNews.newMemberScrapNews(memberScrapNews, member, newsArchive);
        memberScrapNewsRepository.save(memberScrapNews);
        return memberScrapNews.getId();
    }

    @Override
    @Transactional
    public void removeNewsArchive(Long memberId, String url) {
        if (memberScrapNewsRepository.countByMemberId(memberId) > 0){
            MemberScrapNews memberScrapNews = memberScrapNewsRepository.findByMemberId(memberId);
            Long memberScrapNewsId = memberScrapNews.getId();
            List<MemberScrapNewsArchive> memberScrapNewsArchiveList = memberScrapNewsArchiveRepository.findByMemberScrapNewsId(memberScrapNewsId);
            List<String> urlList = memberScrapNewsArchiveList.stream()
                    .map(n -> n.getNewsArchive().getNews().getUrl())
                    .collect(Collectors.toList());
            memberScrapNews.removeMemberScrapNewsArchive(urlList);
        }
    }
}
