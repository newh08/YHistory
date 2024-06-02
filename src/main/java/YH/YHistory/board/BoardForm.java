package YH.YHistory.board;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardForm {
    @NotBlank
    private String title;

    private String contents;

    @NotNull
    private Long categoryId;
}
