package ohai.newslang.domain.subscribe.item;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ohai.newslang.domain.subscribe.MemberSubscribeItem;

import javax.persistence.*;

@Entity
@Getter @Setter
//@NoArgsConstructor
public class MediaItem {

    @Id
    @GeneratedValue
    @Column(name = "media_item_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String mediaGroup;

    @Column
    private String parameterId;

    @Column
    private String imagePath;

//    @Builder
//    public MediaItem(String name, String group, String parameterId, String imagePath) {
//        this.name = name;
//        this.group = group;
//        this.parameterId = parameterId;
//        this.imagePath = imagePath;
//    }
}
