package YH.YHistory.board;

import YH.YHistory.category.Category;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.stream.Collectors;
import lombok.Getter;

@Entity
@Getter
public class Board {

    @Id
    @GeneratedValue
    @Column(name = "board_id")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    private String title;

    private String contents;

    private String created_date;

    private String updated_date;

    public static Board createBoard(BoardForm boardForm, Category category) {
        Board board = new Board();
        board.title = boardForm.getTitle();
        board.contents = boardForm.getContents();
        board.category = category;
        category.getBoards().add(board);

        return board;
    }

    public void setCreatedDate() {
        created_date = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
    }

    public void setUpdated_date() {
        updated_date = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
    }

    public void alterLineSpace() {
        contents = String.join("<br>", contents.split(" \n"));
    }


    public void update(BoardForm boardForm, Category category) {
        this.title = boardForm.getTitle();
        this.contents = boardForm.getContents();
        this.category = category;
    }
}
