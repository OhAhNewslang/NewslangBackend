package ohai.newslang.domain.dto.opinion.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ohai.newslang.domain.dto.request.RequestResult;
import ohai.newslang.domain.entity.opinion.Opinion;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class OpinionResponseDto /*implements Comparator<OpinionResponseDto>*/ {
    private Long opinionId;
    private String memberName;
    private String memberImagePath;
    private LocalDate opinionCreateDate;
    private String opinionContent;
    private Long likeCount;
    private RequestResult result;
    @Builder
    public OpinionResponseDto(Opinion opinion, Long likeCount, RequestResult result) {
        this.opinionId = opinion.getId();
        this.memberName = opinion.getMember().getName();
        this.memberImagePath = opinion.getMember().getImagePath();
        this.opinionCreateDate = opinion.getCreateTime().toLocalDate();
        this.opinionContent = opinion.getContent();
        this.likeCount = likeCount;
        this.result = result;
    }

//    @Override
//    public int compare(OpinionResponseDto o1, OpinionResponseDto o2) {
//        if (o1.opinionCreateDate.isAfter(o2.opinionCreateDate)) {
//            return -1;
//        }else if (o1.opinionCreateDate.equals(o2.opinionCreateDate)){
//            return (int) (o1.likeCount - o2.likeCount);
//        } else {
//            return 1;
//        }
//    }
}
