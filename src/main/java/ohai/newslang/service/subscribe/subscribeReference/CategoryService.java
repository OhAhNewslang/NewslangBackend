package ohai.newslang.service.subscribe.subscribeReference;

import ohai.newslang.domain.entity.subscribe.subscribeReference.Category;

import java.util.List;

public interface CategoryService {
    void save(Category category);
    List<Category> findAll();
}
