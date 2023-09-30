package ohai.newslang.domain.entity.subscribe.subscribeReference;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor
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