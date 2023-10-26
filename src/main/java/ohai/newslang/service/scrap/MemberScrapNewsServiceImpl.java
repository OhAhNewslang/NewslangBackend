package ohai.newslang.service.scrap;

import lombok.RequiredArgsConstructor;
import ohai.newslang.configuration.jwt.TokenDecoder;
import ohai.newslang.domain.dto.page.ResponsePageSourceDto;
import ohai.newslang.domain.dto.request.RequestResult;
import ohai.newslang.domain.dto.scrap.ResultScrapNewsDto;
import ohai.newslang.domain.dto.scrap.ScrapNewsDto;
import ohai.newslang.domain.entity.member.Member;
import ohai.newslang.domain.entity.news.NewsArchive;
import ohai.newslang.domain.entity.scrap.MemberScrapNews;
import ohai.newslang.domain.entity.scrap.MemberScrapNewsArchive;
import ohai.newslang.repository.news.NewsArchiveRepository;
import ohai.newslang.repository.member.MemberRepository;
import ohai.newslang.repository.scrap.MemberScrapNewsArchiveRepository;
import ohai.newslang.repository.scrap.MemberScrapNewsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberScrapNewsServiceImpl implements MemberScrapNewsService {

    private final MemberScrapNewsRepository memberScrapNewsRepository;
    private final MemberScrapNewsArchiveRepository memberScrapNewsArchiveRepository;
    private final MemberRepository memberRepository;
    private final NewsArchiveRepository newsArchiveRepository;
    private final TokenDecoder td;

    @Override
    public ResultScrapNewsDto scarpNewsList(int page, int limit) {
        Long memberId = td.currentMemberId();
        PageRequest pageable = PageRequest.of(page - 1, limit, Sort.by("scrapDateTime").descending());

        Page<MemberScrapNewsArchive> pagingScrapNews = memberScrapNewsArchiveRepository.findByMemberId(memberId, pageable);

        if (pagingScrapNews.getTotalElements() < 1) {
            return ResultScrapNewsDto.builder()
            .result(RequestResult.builder()
            .resultCode("404")
            .resultMessage("스크랩 뉴스가 없습니다.").build())
            .build();
        }

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // DTO리스트로 변환
        List<ScrapNewsDto> scrapNewsDtos = pagingScrapNews.stream()
        .map(MemberScrapNewsArchive::getNewsArchive)
        .map(n -> ScrapNewsDto.builder()
        .newsUrl(n.getUrl())
        .mediaName(n.getMediaName())
        .category(n.getCategory())
        .title(n.getTitle())
        .imagePath(n.getImagePath())
        .postDateTime(n.getPostDateTime().format(dateFormat))
        .scrapDateTime(n.getModifyDateTime().format(dateFormat))
        .build()).collect(Collectors.toList());

        return ResultScrapNewsDto.builder()
        .scrapNewsList(scrapNewsDtos)
        .pageSource(ResponsePageSourceDto.builder()
        .page(page)
        .limit(limit)
        .totalPage(pagingScrapNews.getTotalPages()).build())
        .result(RequestResult.builder()
        .resultCode("200")
        .resultMessage("스크랩 뉴스 목록 조회 성공")
        .build()).build();
    }

    @Override
    @Transactional
    public RequestResult addScrapNews(String newsUrl) {
        if (isExistScrapNews(newsUrl)){
            return RequestResult.builder().resultMessage("이미 스크랩한 뉴스입니다.").resultCode("202").build();
        }
        Long memberId = td.currentMemberId();
        NewsArchive newsArchive = newsArchiveRepository.findByUrl(newsUrl);
        Member member = memberRepository.findByTokenId(memberId);
        MemberScrapNews memberScrapNews = MemberScrapNews.newMemberScrapNews(member, newsArchive);
        memberScrapNewsRepository.save(memberScrapNews);
        return RequestResult.builder()
        .resultCode("200")
        .resultMessage("뉴스 스크랩 성공").build();
    }

    // 이미 스크랩된 뉴스인지 확인.
    public boolean isExistScrapNews(String newsUrl) {
        Long memberId = td.currentMemberId();
        if (memberScrapNewsRepository.countByMemberId(memberId) > 0){
            MemberScrapNews memberScrapNews = memberScrapNewsRepository.findByMemberId(memberId);
            List<MemberScrapNewsArchive> memberScrapNewsArchiveList = memberScrapNews.getMemberScrapNewsArchiveList();
            for (MemberScrapNewsArchive item : memberScrapNewsArchiveList) {
                if (item.getNewsArchive().getUrl().equals(newsUrl)){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    @Transactional
    public void removeScrapNews(String url) {
        Long memberId = td.currentMemberId();
        if (memberScrapNewsRepository.countByMemberId(memberId) > 0){
            MemberScrapNews memberScrapNews = memberScrapNewsRepository.findByMemberId(memberId);
            List<MemberScrapNewsArchive> memberScrapNewsArchiveList =
            memberScrapNewsArchiveRepository.findByMemberId(memberId);
            List<String> urlList = memberScrapNewsArchiveList.stream()
            .map(n -> n.getNewsArchive().getUrl())
            .collect(Collectors.toList());
            memberScrapNews.removeMemberScrapNewsArchive(urlList);
        }
    }
}
