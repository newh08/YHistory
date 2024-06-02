package YH.YHistory.board;

import YH.YHistory.category.Category;
import YH.YHistory.category.CategoryService;
import YH.YHistory.util.LoginSessionUtil;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final CategoryService categoryService;

    @GetMapping("/boards/new")
    public String createForm(Model model, HttpServletRequest request) {
        if (!LoginSessionUtil.isLoginThenAddMemberAtModel(request, model)) {
            return "redirect:/logins/loginForm";
        }
        model.addAttribute("boardForm", new BoardForm());
        model.addAttribute("categories", categoryService.findCategories());
        return "boards/createBoardForm";
    }

    @PostMapping("/boards/new")
    public String create(@Validated BoardForm boardForm,
                         BindingResult result) {
        if (result.hasErrors()) {
            return "boards/createBoardForm";
        }
        boardService.create(boardForm);
        return "redirect:/boards";
    }

    @GetMapping("/boards")
    public String list(Model model, HttpServletRequest request) {
        if (!LoginSessionUtil.isLoginThenAddMemberAtModel(request, model)) {
            return "redirect:/logins/loginForm";
        }
        List<Board> boards = boardService.findAll();
        model.addAttribute("categories", categoryService.findCategories());
        model.addAttribute("boards", boards);
        return "boards/boardList";
    }

    @GetMapping("/boards/c")
    public String listWithCategory(@RequestParam("category") long id,
                                   @RequestParam("titleSearch") String titleSearch,
                                   Model model) {
        Category category = categoryService.findOne(id);
        List<Board> boards = boardService.findBoards(category, titleSearch);
        model.addAttribute("categories", categoryService.findCategories());
        model.addAttribute("boards", boards);
        return "boards/boardList";
    }



    @GetMapping("boards/{boardId}/view")
    public String viewBoard(@PathVariable("boardId") Long boardId, Model model, HttpServletRequest request) {
        if (!LoginSessionUtil.isLoginThenAddMemberAtModel(request, model)) {
            return "redirect:/logins/loginForm";
        }
        Board board = boardService.findOne(boardId);
        board.alterLineSpace();
        model.addAttribute("board", board);
        return "boards/board";
    }

    @GetMapping("boards/{boardId}/edit")
    public String updateBoardForm(@PathVariable("boardId") Long boardId, Model model, HttpServletRequest request) {
        if (!LoginSessionUtil.isLoginThenAddMemberAtModel(request, model)) {
            return "redirect:/logins/loginForm";
        }
        Board board = boardService.findOne(boardId);
        BoardForm boardForm = new BoardForm();
        boardForm.setTitle(board.getTitle());
        boardForm.setContents(board.getContents());
        boardForm.setCategoryId(board.getCategory().getId());
        model.addAttribute("boardForm", boardForm);
        model.addAttribute("categories", categoryService.findCategories());
        return "boards/updateBoardForm";
    }

    @PostMapping("boards/{boardId}/edit")
    public String updateBoard(@PathVariable("boardId") Long boardId, @ModelAttribute("boardForm") BoardForm boardForm) {
        boardService.update(boardId, boardForm);

        return "redirect:/boards";
    }

    @GetMapping("/boards/{boardId}/delete")
    public String deleteBoard(@PathVariable("boardId") Long boardId) {
        boardService.deleteById(boardId);
        return ("redirect:/boards");
    }
}
