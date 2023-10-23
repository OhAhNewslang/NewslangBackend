package ohai.newslang.service.subscribe.subscribeReference;

import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.entity.subscribe.subscribeReference.Category;
import ohai.newslang.repository.subscribe.subscribeReference.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public void save(Category category){
        categoryRepository.save(category);
    }

    @Override
    @Transactional
    public void saveAll(List<Category> categoryList){
        categoryRepository.saveAll(categoryList);
    }

    @Override
    public boolean isExistCategoryName(String categoryName){
        return categoryRepository.countByName(categoryName) > 0;
    }

    @Override
    public List<String> findAll(){
        return categoryRepository.findAll().stream()
        .map(Category::getName)
        .toList();
    }
}