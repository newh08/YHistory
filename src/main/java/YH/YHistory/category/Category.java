package YH.YHistory.category;

import YH.YHistory.board.Board;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Entity
@Getter
public class Category {
    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String category;

    @JsonIgnore
    @OneToMany(mappedBy = "category")
    private List<Board> boards = new ArrayList<>();

    private String created_date;

    public static Category makeCategory(CategoryForm categoryForm) {
        Category category = new Category();
        category.category = categoryForm.getCategory();
        return category;
    }

    public void setCreatedDate() {
        created_date = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
    }

    public boolean isAll() {
        return this.category.equals("ALL");
    }

    public void updateCategory(String category) {
        this.category = category;
    }
}
