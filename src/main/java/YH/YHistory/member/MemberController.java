package YH.YHistory.member;

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

        try {
            memberService.join(member);
        } catch (IllegalStateException e) {
            result.reject("sameUserName", e.getMessage());
        }
        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model) {

        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}
