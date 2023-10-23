package ohai.newslang.domain.dto.page;

import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.parameters.P;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RequestPageSourceDto {

    private int page;
    private int limit;

    @Builder
    public RequestPageSourceDto(int page, int limit) {
        this.page = page;
        this.limit = limit;
    }
}
