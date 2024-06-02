package YH.YHistory.login;

import YH.YHistory.exception.NotCorrespondingUserIdException;
import YH.YHistory.member.Member;
import YH.YHistory.session.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    @PostMapping("/login")
    @ExceptionHandler(NotCorrespondingUserIdException.class)
    public String login(@Validated LoginForm form, BindingResult bindingResult, HttpServletRequest request) {

        // LoginForm 에 userId 혹은 password 의 값이 존재하지 않을 때
        if (bindingResult.hasErrors()) {
            return "/";
        }

        Member loginMember = loginService.login(form.getUserId(), form.getPassword());

        /**
         * 로그인 성공 처리
         **/
        //세션이 있으면 있는 세션 반환, 없으면 신규 세션을 생성
        HttpSession session = request.getSession();
        //세션에 로그인 회원 정보 보관
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);
        return "redirect:/";
    }


    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }

    @GetMapping("/logins/loginForm")
    public String login(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "/logins/login";
    }
}
