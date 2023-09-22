package ohai.newslang.domain.subscribe.reference;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
//@NoArgsConstructor
public class Media {

    @Id
    @GeneratedValue
    @Column(name = "media_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column
    private String mediaGroup;

    @Column
    private String parameterId;

    @Column
    private String imagePath;
}
