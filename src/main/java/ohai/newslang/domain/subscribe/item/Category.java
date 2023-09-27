package ohai.newslang.domain.subscribe.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@DiscriminatorValue("CATEGORY")
@Getter @Setter
public class Category extends SubscribeItem{
}
