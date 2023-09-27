package ohai.newslang.domain.subscribe.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@DiscriminatorValue("MEDIA")
@Getter @Setter
public class Media extends SubscribeItem{
    private String imagePath;
}
