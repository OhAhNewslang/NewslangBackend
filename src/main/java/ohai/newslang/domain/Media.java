package ohai.newslang.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
public class Media {

    @Id @GeneratedValue
    @Column(name = "media_id")
    private Long id;

    private String name;

    private String imagePath;
}
