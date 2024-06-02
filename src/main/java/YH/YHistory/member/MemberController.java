package YH.YHistory.member;

import YH.YHistory.session.SessionConst;
import YH.YHistory.util.LoginSessionUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberDTO", new MemberDTO());
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(@Validated MemberDTO memberDTO, BindingResult result) {

        if (result.hasErrors()) {
            return "members/createMemberForm";
        }


        Member member = new Member(memberDTO);

        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model, HttpServletRequest request) {
        if (!LoginSessionUtil.isLoginThenAddMemberAtModel(request,model)) {
            return "redirect:/logins/loginForm";
        }

        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}
