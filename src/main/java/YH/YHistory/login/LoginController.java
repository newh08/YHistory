package YH.YHistory.login;

import YH.YHistory.exception.NotCorrespondingUserIdException;
import YH.YHistory.member.Member;
import YH.YHistory.session.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    @GetMapping("/login")
    public String login(Model model, @RequestParam(defaultValue = "/") String redirectURL) {
        model.addAttribute("loginForm", new LoginForm());
        model.addAttribute("redirectURL", redirectURL);

        return "/logins/login";
    }

    @PostMapping("/login")
    public String login(@Validated LoginForm form,
                        BindingResult bindingResult,
                        HttpServletRequest request,
                        @ModelAttribute("redirectURL") String redirectURL) {

        // LoginForm 에 userId 혹은 password 의 값이 존재하지 않을 때
        if (bindingResult.hasErrors()) {
            return "home";
        }

        Member loginMember;

        try {
            loginMember = loginService.login(form.getUserId(), form.getPassword());
        } catch (NotCorrespondingUserIdException e) {
            loginMember = null;
            bindingResult.addError(new ObjectError("wrongId", "존재하지 않는 아이디입니다."));
        } catch (IllegalStateException e) {
            loginMember = null;
            bindingResult.addError(new ObjectError("wrongPassword", "비밀번호가 틀렸습니다."));
        }

        if (bindingResult.hasErrors()) {
            return "home";
        }

        /**
         * 로그인 성공 처리
         **/
        //세션이 있으면 있는 세션 반환, 없으면 신규 세션을 생성
        HttpSession session = request.getSession();
        //세션에 로그인 회원 정보 보관
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);

        System.out.println(redirectURL);
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


}
