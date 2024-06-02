package YH.YHistory.board;

import YH.YHistory.category.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardForm {
    private String title;

    private String contents;

    private Long categoryId;
}
