package ohai.newslang.domain.entity.subscribeReference;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Category {

    @Id
    @GeneratedValue
    @Column(name = "subscribe_category_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    public void setName(String name){
        this.name = name;
    }
}