package ohai.newslang.service.subscribe.subscribeReference;

import ohai.newslang.domain.entity.subscribe.subscribeReference.Category;

import java.util.List;

public interface CategoryService {
    void save(Category category);

    void saveAll(List<Category> categoryList);

    boolean isExistCategoryName(String categoryName);

    List<String> findAll();
}
