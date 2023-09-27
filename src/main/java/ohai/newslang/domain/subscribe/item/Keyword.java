package ohai.newslang.domain.subscribe.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@DiscriminatorValue("KEYWORD")
@Getter @Setter
public class Keyword extends SubscribeItem{
}
