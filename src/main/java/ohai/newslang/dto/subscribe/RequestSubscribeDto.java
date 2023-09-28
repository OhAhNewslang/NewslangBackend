package ohai.newslang.dto.subscribe;

import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RequestSubscribeDto {

    private List<String> nameList;
}
