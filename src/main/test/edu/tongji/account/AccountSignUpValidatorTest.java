package edu.tongji.account;

import org.apache.tomcat.jni.Error;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Created by summer on 6/23/16.
 */
public class AccountSignUpValidatorTest {

    @Test
    public void testSupports() throws Exception {
        AccountSignUpValidator accountSignUpValidator = new AccountSignUpValidator();

        //test true case
        Account testTrue = new Account();
        assertEquals(true,accountSignUpValidator.supports(testTrue.getClass()));

        //test false case
        Integer testFalse = new Integer(10);
        assertEquals(false,accountSignUpValidator.supports((testFalse.getClass())));

        //test null case
        assertEquals(false,accountSignUpValidator.supports(null));

    }

    @Test
    public void testValidate() throws Exception {
        AccountSignUpValidator accountSignUpValidator = new AccountSignUpValidator();

        Account account1 = new Account();
        account1.setEmail("");
        account1.setNickname("");
        Errors error1 = new BeanPropertyBindingResult(account1,"");
        accountSignUpValidator.validate(account1,error1);
        boolean result1 = error1.hasFieldErrors("email");
        boolean result2 = error1.hasFieldErrors("nickname");
        boolean result = result1 || result2;
        assertEquals(false,result);

    }
}