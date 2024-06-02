package YH.YHistory.board;

import YH.YHistory.category.Category;
import jakarta.persistence.EntityManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BoardRepository {
    private final EntityManager em;

    public void save(Board board) {
        if (board.getId() == null) {
            em.persist(board);
        } else {
            em.merge(board);
        }
    }

    public List<Board> findBoards(Category category) {
        return em.createQuery("select b from Board b where b.category = :category", Board.class)
                .setParameter("category", category)
                .getResultList();
    }

    public List<Board> findWithTitleSearch(String titleSearch) {
        return em.createQuery("select b from Board b where b.title like :titleSearch", Board.class)
                .setParameter("titleSearch", "%" + titleSearch + "%")
                .getResultList();
    }

    public List<Board> findBoardWithCategoryAndTitleSearch(Category category, String titleSearch) {
        return em.createQuery("select b from Board b where b.category = :category and b.title like :titleSearch",
                        Board.class)
                .setParameter("category", category)
                .setParameter("titleSearch", "%" + titleSearch + "%")
                .getResultList();
    }

    public List<Board> findAll() {
        return em.createQuery("select b from Board b", Board.class)
                .getResultList();
    }

    public Board findOne(Long boardId) {
        return em.find(Board.class, boardId);
    }

    public void deleteById(Long boardId) {
        em.remove(findOne(boardId));
    }
}
