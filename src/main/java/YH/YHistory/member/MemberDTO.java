package YH.YHistory.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDTO {

    @NotBlank
    private String userId;
    @NotBlank
    private String password;
    @NotBlank
    private String name;
    @NotBlank @Email
    private String email;
}
