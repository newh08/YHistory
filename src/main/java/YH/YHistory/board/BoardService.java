package YH.YHistory.board;

import YH.YHistory.category.Category;
import YH.YHistory.category.CategoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public Long create(BoardForm boardForm) {
        Category category = categoryRepository.findOne(boardForm.getCategoryId());

        Board board = Board.createBoard(boardForm, category);
        board.setCreatedDate();

        boardRepository.save(board);
        return board.getId();
    }

    @Transactional
    public void update(Long boardId, BoardForm boardForm) {
        Board findBoard = findOne(boardId);
        Category category = categoryRepository.findOne(boardForm.getCategoryId());
        findBoard.update(boardForm, category);
        findBoard.setUpdated_date();

    }

    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    public List<Board> findBoards(Category category, String titleSearch) {

        if (category == null) {
        // 이름 검색만
            return boardRepository.findWithTitleSearch(titleSearch);
        }

        if (titleSearch == null) {
            //카테고리 검색만
            if (category.isAll()) {
                return boardRepository.findAll();
            }
            return boardRepository.findBoards(category);
        }
        //이름, 카테고리검색 같이
        if (category.isAll()) {
            return boardRepository.findWithTitleSearch(titleSearch);
        }
        return boardRepository.findBoardWithCategoryAndTitleSearch(category, titleSearch);
    }

    public Board findOne(Long boardId) {
        return boardRepository.findOne(boardId);

    }

    @Transactional
    public void deleteById(Long boardId) {
        boardRepository.deleteById(boardId);
    }
}
