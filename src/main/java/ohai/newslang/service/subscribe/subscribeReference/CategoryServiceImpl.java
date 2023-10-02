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
    public List<Category> findAll(){
        return  categoryRepository.findAll();
    }
}