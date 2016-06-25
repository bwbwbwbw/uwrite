package edu.tongji.signup;

import edu.tongji.account.Account;
import edu.tongji.account.AccountRepository;
import edu.tongji.account.AccountService;
import edu.tongji.account.AccountSignUpValidator;
import edu.tongji.error.ConstraintException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class SignupController {

    private static final String SIGNUP_VIEW_NAME = "signup/signup";

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountSignUpValidator accountSignUpValidator;

    @RequestMapping(value = "signup")
    public String signup(Model model) {
        model.addAttribute(new SignupForm());
        return SIGNUP_VIEW_NAME;
    }

    @RequestMapping(value = "signup", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public Account signup(@Valid @ModelAttribute SignupForm signupForm, BindingResult bindingResult) {
        Account account = signupForm.createAccount();
        accountSignUpValidator.validate(account, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new ConstraintException(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        account = accountService.signUp(account);
        accountService.signIn(account);
        return account;
    }


}
