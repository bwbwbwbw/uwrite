package edu.tongji.account;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * @testType UNIT_TEST
 */
@RunWith(MockitoJUnitRunner.class)
public class AccountSignUpValidatorTest {

    @InjectMocks
    private AccountSignUpValidator accountSignUpValidator;

    @Mock
    private AccountService accountServiceMock;

    /**
     * @unitTestTarget AccountSignUpValidator.supports
     * @unitTestType 等价类测试
     * @unitTestDescription
     */
    @Test
    public void testSupports1() throws Exception {
        Account testTrue = new Account();
        assertEquals(true, accountSignUpValidator.supports(testTrue.getClass()));
    }

    /**
     * @unitTestTarget AccountSignUpValidator.supports
     * @unitTestType 等价类测试
     * @unitTestDescription
     */
    @Test
    public void testSupports2() throws Exception {
        Object testFalse = new Object();
        assertEquals(false, accountSignUpValidator.supports((testFalse.getClass())));
    }

    /**
     * @unitTestTarget AccountSignUpValidator.supports
     * @unitTestType 等价类测试
     * @unitTestDescription
     */
    @Test
    public void testSupports3() throws Exception {
        assertEquals(false, accountSignUpValidator.supports(null));
    }

    /**
     * @unitTestTarget AccountSignUpValidator.validate
     * @unitTestType 等价类测试
     * @unitTestDescription
     */
    @Test
    public void testValidate1() throws Exception {
        // act
        Account account = new Account();
        account.setEmail("");
        account.setNickname("");
        Errors error = new BeanPropertyBindingResult(account, "");
        accountSignUpValidator.validate(account, error);

        // assert
        assertTrue(error.hasFieldErrors("email"));
        assertTrue(error.hasFieldErrors("nickname"));
    }

    /**
     * @unitTestTarget AccountSignUpValidator.validate
     * @unitTestType 等价类测试
     * @unitTestDescription
     */
    @Test
    public void testValidate2() throws Exception {
        // arrange
        Account demoAccount = new Account("test@example.com", "test", "test_user", "ROLE_USER");
        when(accountServiceMock.findByEmail("test@example.com")).thenReturn(demoAccount);
        when(accountServiceMock.findByNickname("test_user")).thenReturn(demoAccount);

        // act
        Account account = new Account();
        account.setEmail("test2@example.com");
        account.setNickname("test_user");
        Errors error = new BeanPropertyBindingResult(account, "");
        accountSignUpValidator.validate(account, error);

        // assert
        assertFalse(error.hasFieldErrors("email"));
        assertTrue(error.hasFieldErrors("nickname"));
    }

}