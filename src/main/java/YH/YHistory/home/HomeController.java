package YH.YHistory.home;

import YH.YHistory.login.LoginForm;
import YH.YHistory.login.LoginService;
import YH.YHistory.member.Member;
import YH.YHistory.session.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {
    private final LoginService loginService;

    @GetMapping("/")
    public String homeLogin(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member,
                            Model model) {
        if (member == null) {
            model.addAttribute("loginForm", new LoginForm());
        }

        model.addAttribute("member", member);
        return "home";
    }

}
