package YH.YHistory.category;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Transactional
    public Long add(Category category) {
        validateDuplicateCategory(category);
        category.setCreatedDate();
        categoryRepository.save(category);
        return category.getId();
    }

    @Transactional
    public void updateCategory(Long categoryId, String category) {
        Category findCategory = categoryRepository.findOne(categoryId);
        findCategory.updateCategory(category);
        findCategory.setCreatedDate();
    }

    private void validateDuplicateCategory(Category category) {
        List<Category> categories = categoryRepository.findByName(category.getCategory());
        if (!categories.isEmpty()) {
            throw new IllegalStateException("이미 동일한 카테고리가 있습니다.");
        }
    }

    public List<Category> findCategories() {
        return categoryRepository.findAll();
    }

    public Category findOne(Long categoryId) {
        return categoryRepository.findOne(categoryId);
    }

    @Transactional
    public void deleteById(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }

}
