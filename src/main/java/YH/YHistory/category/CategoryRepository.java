package YH.YHistory.category;

import jakarta.persistence.EntityManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CategoryRepository {

    private final EntityManager em;

    public void save(Category category) {
        em.persist(category);
    }

    public List<Category> findAll() {
        return em.createQuery("select c from Category c", Category.class)
                .getResultList();
    }

    public List<Category> findByName(String category) {
        return em.createQuery("select c from Category c where c.categoryName = :category", Category.class)
                .setParameter("category", category)
                .getResultList();
    }

    public Category findOne(Long categoryId) {
        return em.find(Category.class, categoryId);
    }

    public void deleteById(Long categoryId) {
        em.remove(findOne(categoryId));
    }
}
