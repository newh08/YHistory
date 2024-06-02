package YH.YHistory.validator;

import YH.YHistory.member.Member;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class MemberValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Member.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

    }
}
