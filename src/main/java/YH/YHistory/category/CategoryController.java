package YH.YHistory.category;

import YH.YHistory.util.LoginSessionUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
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

@Controller
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/categories/new")
    public String createForm(Model model, HttpServletRequest request) {
        if (!LoginSessionUtil.isLoginThenAddMemberAtModel(request, model)) {
            return "redirect:/logins/loginForm";
        }
        model.addAttribute("categoryForm", new CategoryForm());
        return "categories/createCategoryForm";
    }

    @PostMapping("/categories/new")
    public String create(@Validated CategoryForm categoryForm, BindingResult result) {
        if (result.hasErrors()) {
            return "categories/createCategoryForm";
        }

        Category category = Category.makeCategory(categoryForm);
        categoryService.add(category);
        return "redirect:/";
    }

    @GetMapping("/categories")
    public String list(Model model, HttpServletRequest request) {
        if (!LoginSessionUtil.isLoginThenAddMemberAtModel(request, model)) {
            return "redirect:/logins/loginForm";
        }
        List<Category> categoryList = categoryService.findCategories();
        model.addAttribute("categories", categoryList);
        return "categories/categoryList";
    }

    @GetMapping("categories/{categoryId}/edit")
    public String updateCategoryForm(@PathVariable("categoryId") Long categoryId, Model model, HttpServletRequest request) {
        if (!LoginSessionUtil.isLoginThenAddMemberAtModel(request, model)) {
            return "redirect:/logins/loginForm";
        }
        Category category = categoryService.findOne(categoryId);
        CategoryForm categoryForm = new CategoryForm();
        categoryForm.setCategory(category.getCategory());
        model.addAttribute("categoryForm", categoryForm);
        return ("categories/updateCategoryForm");
    }

    @PostMapping("categories/{categoryId}/edit")
    public String updateCategory(@PathVariable("categoryId") Long categoryId, @ModelAttribute("categoryForm") CategoryForm categoryForm) {
        categoryService.updateCategory(categoryId, categoryForm.getCategory());
        return ("redirect:/categories");
    }

    @GetMapping("categories/{categoryId}/delete")
    public String deleteCategory(@PathVariable("categoryId") Long categoryId, Model model, HttpServletRequest request) {
        if (!LoginSessionUtil.isLoginThenAddMemberAtModel(request, model)) {
            return "redirect:/logins/loginForm";
        }
        categoryService.deleteById(categoryId);
        return ("redirect:/categories");
    }
}
