package ohai.newslang.domain.dto.request;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestResult {
    private boolean isSuccess;
    private String failCode;
}
