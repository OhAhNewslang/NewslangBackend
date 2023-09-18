package ohai.newslang.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Getter @Setter
@NoArgsConstructor
public class News {
    private String url;
    private String mediaName;
    private String categoryName;
    private String writer;
    private String title;
    private String contents;
}
