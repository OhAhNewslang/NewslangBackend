package ohai.newslang.domain.subscribe.item;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter
@Setter
public abstract class SubscribeItem {

    @Id
    @GeneratedValue
    @Column(name = "subscribe_item_id")
    private Long id;

    @Column(nullable = false)
    private String name;
}
