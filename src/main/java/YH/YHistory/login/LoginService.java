package YH.YHistory.login;

import YH.YHistory.exception.NotCorrespondingUserIdException;
import YH.YHistory.member.Member;
import YH.YHistory.member.MemberRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LoginService {
    private final MemberRepository memberRepository;

    public Member login(String userId, String password) {
        Optional<Member> findMember = memberRepository.findByUserId(userId);
        if (!findMember.orElseThrow(() -> new NotCorrespondingUserIdException("존재하지 않는 아이디 입니다.")).checkPassword(password)) {
            throw new IllegalStateException("아이디와 비밀번호가 일치하지 않습니다.");
        }
        return findMember.get();
    }


}
