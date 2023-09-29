package ohai.newslang.service.subscribe;

import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.entity.subscribeReference.Category;
import ohai.newslang.repository.subscribe.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    @Transactional
    public void save(Category category){
        categoryRepository.save(category);
    }

    public List<Category> findSubscribeItemList(){
        return  categoryRepository.findAll();
    }
}