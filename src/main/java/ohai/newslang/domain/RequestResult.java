package ohai.newslang.domain;

import lombok.*;

@Data
@NoArgsConstructor
public class RequestResult {
    private boolean isSuccess;
    private String failCode;

    @Builder
    public RequestResult(boolean isSuccess, String failCode) {
        this.isSuccess = isSuccess;
        this.failCode = failCode;
    }
}
