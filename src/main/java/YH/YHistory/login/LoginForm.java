package YH.YHistory.login;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginForm {
    @NotBlank
    private String userId;
    @NotBlank
    private String password;

}
