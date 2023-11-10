package ohai.newslang.domain.dto.gpt;

import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    private String role;
    private String content;
}