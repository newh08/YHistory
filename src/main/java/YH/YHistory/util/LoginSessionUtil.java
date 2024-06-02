package YH.YHistory.util;

import YH.YHistory.member.Member;
import YH.YHistory.session.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.Optional;
import org.springframework.ui.Model;

public class LoginSessionUtil {
    public static boolean isLoginThenAddMemberAtModel(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        if (member != null) {
            model.addAttribute("member", member);
            return true;
        }
        return false;
    }
}
