package edu.tongji.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class AccountSignUpValidator implements Validator {

    @Autowired
    private AccountService accountService;

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final Pattern emailPattern = Pattern.compile(EMAIL_PATTERN);

    @Override
    public boolean supports(Class<?> clazz) {
        return Account.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Account account = (Account) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", null, "Email is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nickname", null, "Nickname is required");
        if (!emailPattern.matcher(account.getEmail()).matches()) {
            errors.rejectValue("email", null, "Email is not valid");
        }
        if (accountService.findByEmail(account.getEmail()) != null) {
            errors.rejectValue("email", null, "Email is already taken");
        }
        if (accountService.findByNickname(account.getNickname()) != null) {
            errors.rejectValue("nickname", null, "Nickname is already taken");
        }
    }

}
