package ohai.newslang.domain.dto.request;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestResult {
    private String resultCode;
    private String resultMessage;
}
