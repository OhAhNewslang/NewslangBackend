package ohai.newslang.domain.dto.news;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ohai.newslang.domain.dto.request.RequestResult;
import ohai.newslang.domain.enumulate.RecommendStatus;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ResultDetailNewsDto {

    private String url;
    private String title;
    private String contents;
    private String media;
    private int likeCount;
    private String  recommend;
    private LocalDateTime postDateTime;
    private LocalDateTime modifyDateTime;
    private String reporter;
    private RequestResult result;

    @Builder
    public ResultDetailNewsDto(String url, String title, String contents, String media, int likeCount, RecommendStatus recommend, LocalDateTime postDateTime, LocalDateTime modifyDateTime, String reporter, RequestResult result) {
        this.url = url;
        this.title = title;
        this.contents = contents;
        this.media = media;
        this.likeCount = likeCount;
        this.recommend = String.valueOf(recommend);
        this.postDateTime = postDateTime;
        this.modifyDateTime = modifyDateTime;
        this.reporter = reporter;
        this.result = result;
    }
}
