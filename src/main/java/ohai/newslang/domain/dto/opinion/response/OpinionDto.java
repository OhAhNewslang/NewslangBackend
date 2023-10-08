package ohai.newslang.domain.dto.opinion.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ohai.newslang.domain.dto.request.RequestResult;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OpinionDto {
    String memberName;
    String memberImagePath;
    LocalDate opinionCreateDate;
    String opinionContent;
    Long likeCount;
    RequestResult result;
}
