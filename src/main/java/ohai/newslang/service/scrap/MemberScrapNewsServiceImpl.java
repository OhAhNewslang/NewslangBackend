package ohai.newslang.service.scrap;

import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.entity.member.Member;
import ohai.newslang.domain.entity.scrap.MemberScrapNews;
import ohai.newslang.domain.entity.scrap.MemberScrapNewsArchive;
import ohai.newslang.domain.entity.scrap.ScrapNewsArchive;
import ohai.newslang.repository.member.MemberRepository;
import ohai.newslang.repository.scrap.MemberScrapNewsArchiveRepository;
import ohai.newslang.repository.scrap.MemberScrapNewsRepository;
import ohai.newslang.repository.scrap.ScrapNewsArchiveRepository;
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
    private final ScrapNewsArchiveRepository scrapNewsArchiveRepository;

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
    public Long addNewsArchive(Long memberId, Long scrapNewsArchiveId) {
        MemberScrapNews memberScrapNews = null;
        Optional<ScrapNewsArchive> scrapNewsArchive = scrapNewsArchiveRepository.findById(scrapNewsArchiveId);
        if (memberScrapNewsRepository.countByMemberId(memberId) > 0){
            memberScrapNews = memberScrapNewsRepository.findByMemberId(memberId);
            Long memberScrapNewsId = memberScrapNews.getId();
            List<MemberScrapNewsArchive> memberScrapNewsArchiveList = memberScrapNewsArchiveRepository.findByMemberScrapNewsId(memberScrapNewsId);
            String newUrl = scrapNewsArchive.get().getNewsUrl();
            boolean isAlreadyUrl = false;
            for (MemberScrapNewsArchive item : memberScrapNewsArchiveList) {
                if (item.getScrapNewsArchive().getNewsUrl() == newUrl){
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
        memberScrapNews = MemberScrapNews.newMemberScrapNews(memberScrapNews, member, scrapNewsArchive.get());
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
                    .map(n -> n.getScrapNewsArchive().getNewsUrl())
                    .collect(Collectors.toList());
            memberScrapNews.removeMemberScrapNewsArchive(urlList);
        }
    }
}
