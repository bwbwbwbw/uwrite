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
     * @unitTestId TEST_SIGN_UP_VALIDATOR_1
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
     * @unitTestId TEST_SIGN_UP_VALIDATOR_2
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
     * @unitTestId TEST_SIGN_UP_VALIDATOR_3
     * @unitTestTarget AccountSignUpValidator.supports
     * @unitTestType 等价类测试
     * @unitTestDescription
     */
    @Test
    public void testSupports3() throws Exception {
        assertEquals(false, accountSignUpValidator.supports(null));
    }

    /**
     * @unitTestId TEST_SIGN_UP_VALIDATOR_4
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
     * @unitTestId TEST_SIGN_UP_VALIDATOR_5
     * @unitTestTarget AccountSignUpValidator.validate
     * @unitTestType 等价类测试
     * @unitTestDescription
     */
    @Test
    public void testValidate2() throws Exception {


        // arrange
        Account nicknameAlreadyTaken = new Account("test@example.com", "test", "swx", "ROLE_USER");
        when(accountServiceMock.findByNickname("swx")).thenReturn(nicknameAlreadyTaken);
        when(accountServiceMock.findByEmail("test@example.com")).thenReturn(nicknameAlreadyTaken);

        // act
        Account account = new Account();
        account.setEmail("");
        account.setNickname("swx");
        Errors errors = new BeanPropertyBindingResult(account, "");
        accountSignUpValidator.validate(account, errors);

        //assert
        assertEquals(1, errors.getFieldErrorCount("email"));
        assertEquals("Email is required", errors.getFieldError("email").getDefaultMessage());

        assertEquals(1, errors.getFieldErrorCount("nickname"));
        assertEquals("Nickname is already taken", errors.getFieldError("nickname").getDefaultMessage());
    }

    /**
     * @unitTestId
     * @unitTestTarget AccountSignUpValidator.validate
     * @unitTestType
     * @unitTestDescription
     */
    @Test
    public void testValidate3() throws Exception {
        // arrange
        Account nickNameValid = new Account("test@example.com", "test", "swx", "ROLE_USER");
        when(accountServiceMock.findByNickname("swx")).thenReturn(nickNameValid);
        when(accountServiceMock.findByEmail("test@example.com")).thenReturn(nickNameValid);

        // act
        Account account = new Account();
        account.setEmail("");
        account.setNickname("summer");
        Errors errors = new BeanPropertyBindingResult(account, "");
        accountSignUpValidator.validate(account, errors);

        // assert
        assertEquals(1, errors.getFieldErrorCount("email"));
        assertEquals("Email is required", errors.getFieldError("email").getDefaultMessage());

        assertEquals(0, errors.getFieldErrorCount("nickname"));
    }

    /**
     * @unitTestId
     * @unitTestTarget AccountSignUpValidator.validate
     * @unitTestType
     * @unitTestDescription
     */
    @Test
    public void testValidate4() throws Exception {
        // arrange
        Account nickNameValid = new Account("test@example.com", "test", "swx", "ROLE_USER");
        when(accountServiceMock.findByNickname("swx")).thenReturn(nickNameValid);
        when(accountServiceMock.findByEmail("test@example.com")).thenReturn(nickNameValid);

        // act
        Account account = new Account();
        account.setEmail("123@123");
        account.setNickname("");
        Errors errors = new BeanPropertyBindingResult(account, "");
        accountSignUpValidator.validate(account, errors);

        // assert
        assertEquals(1,errors.getFieldErrorCount("email"));
        assertEquals("Email is not valid", errors.getFieldError("email").getDefaultMessage());

        assertEquals(1,errors.getFieldErrorCount("nickname"));
        assertEquals("Nickname is required", errors.getFieldError("nickname").getDefaultMessage());
    }

    /**
     * @unitTestId
     * @unitTestTarget AccountSignUpValidator.validate
     * @unitTestType
     * @unitTestDescription
     */
    @Test
    public void testValidate5() throws Exception {
        // arrange
        Account nickNameValid = new Account("test@example.com", "test", "swx", "ROLE_USER");
        when(accountServiceMock.findByNickname("swx")).thenReturn(nickNameValid);
        when(accountServiceMock.findByEmail("test@example.com")).thenReturn(nickNameValid);

        // act
        Account account = new Account();
        account.setEmail("123@123");
        account.setNickname("swx");
        Errors errors = new BeanPropertyBindingResult(account, "");
        accountSignUpValidator.validate(account, errors);

        // assert
        assertEquals(1,errors.getFieldErrorCount("email"));
        assertEquals("Email is not valid", errors.getFieldError("email").getDefaultMessage());

        assertEquals(1,errors.getFieldErrorCount("nickname"));
        assertEquals("Nickname is already taken", errors.getFieldError("nickname").getDefaultMessage());
    }

    /**
     * @unitTestId
     * @unitTestTarget AccountSignUpValidator.validate
     * @unitTestType
     * @unitTestDescription
     */
    @Test
    public void testValidate6() throws Exception {
        // arrange
        Account nickNameValid = new Account("test@example.com", "test", "swx", "ROLE_USER");
        when(accountServiceMock.findByNickname("swx")).thenReturn(nickNameValid);
        when(accountServiceMock.findByEmail("test@example.com")).thenReturn(nickNameValid);

        // act
        Account account = new Account();
        account.setEmail("123@123");
        account.setNickname("summer");
        Errors errors = new BeanPropertyBindingResult(account, "");
        accountSignUpValidator.validate(account, errors);

        // assert
        assertEquals(1,errors.getFieldErrorCount("email"));
        assertEquals("Email is not valid", errors.getFieldError("email").getDefaultMessage());

        assertEquals(0,errors.getFieldErrorCount("nickname"));
    }

    /**
     * @unitTestId
     * @unitTestTarget AccountSignUpValidator.validate
     * @unitTestType
     * @unitTestDescription
     */
    @Test
    public void testValidate7() throws Exception {
        // arrange
        Account nickNameValid = new Account("test@example.com", "test", "swx", "ROLE_USER");
        when(accountServiceMock.findByNickname("swx")).thenReturn(nickNameValid);
        when(accountServiceMock.findByEmail("test@example.com")).thenReturn(nickNameValid);

        // act
        Account account = new Account();
        account.setEmail("test@example.com");
        account.setNickname("");
        Errors errors = new BeanPropertyBindingResult(account, "");
        accountSignUpValidator.validate(account, errors);

        // assert
        assertEquals(1,errors.getFieldErrorCount("email"));
        assertEquals("Email is already taken", errors.getFieldError("email").getDefaultMessage());

        assertEquals(1,errors.getFieldErrorCount("nickname"));
        assertEquals("Nickname is required", errors.getFieldError("nickname").getDefaultMessage());
    }

    /**
     * @unitTestId
     * @unitTestTarget AccountSignUpValidator.validate
     * @unitTestType
     * @unitTestDescription
     */
    @Test
    public void testValidate8() throws Exception {
        //arrange
        Account nickNameValid = new Account("test@example.com", "test", "swx", "ROLE_USER");
        when(accountServiceMock.findByNickname("swx")).thenReturn(nickNameValid);
        when(accountServiceMock.findByEmail("test@example.com")).thenReturn(nickNameValid);

        // act
        Account account = new Account();
        account.setEmail("test@example.com");
        account.setNickname("swx");
        Errors errors = new BeanPropertyBindingResult(account, "");
        accountSignUpValidator.validate(account, errors);

        // assert
        assertEquals(1,errors.getFieldErrorCount("email"));
        assertEquals("Email is already taken", errors.getFieldError("email").getDefaultMessage());

        assertEquals(1,errors.getFieldErrorCount("nickname"));
        assertEquals("Nickname is already taken", errors.getFieldError("nickname").getDefaultMessage());
    }

    /**
     * @unitTestId
     * @unitTestTarget AccountSignUpValidator.validate
     * @unitTestType
     * @unitTestDescription
     */
    @Test
    public void testValidate9() throws Exception {
        //arrange
        Account nickNameValid = new Account("test@example.com", "test", "swx", "ROLE_USER");
        when(accountServiceMock.findByNickname("swx")).thenReturn(nickNameValid);
        when(accountServiceMock.findByEmail("test@example.com")).thenReturn(nickNameValid);

        // act
        Account account = new Account();
        account.setEmail("test@example.com");
        account.setNickname("summer");
        Errors errors = new BeanPropertyBindingResult(account, "");
        accountSignUpValidator.validate(account, errors);

        // assert
        assertEquals(1,errors.getFieldErrorCount("email"));
        assertEquals("Email is already taken", errors.getFieldError("email").getDefaultMessage());

        assertEquals(0,errors.getFieldErrorCount("nickname"));

    }

    /**
     * @unitTestId
     * @unitTestTarget AccountSignUpValidator.validate
     * @unitTestType
     * @unitTestDescription
     */
    @Test
    public void testValidate10() throws Exception {
        //arrange
        Account nickNameValid = new Account("test@example.com", "test", "swx", "ROLE_USER");
        when(accountServiceMock.findByNickname("swx")).thenReturn(nickNameValid);
        when(accountServiceMock.findByEmail("test@example.com")).thenReturn(nickNameValid);

        // act
        Account account = new Account();
        account.setEmail("summer@summer.com");
        account.setNickname("");
        Errors errors = new BeanPropertyBindingResult(account, "");
        accountSignUpValidator.validate(account, errors);

        // assert
        assertEquals(0,errors.getFieldErrorCount("email"));

        assertEquals(1,errors.getFieldErrorCount("nickname"));
        assertEquals("Nickname is required", errors.getFieldError("nickname").getDefaultMessage());

    }

    /**
     * @unitTestId
     * @unitTestTarget AccountSignUpValidator.validate
     * @unitTestType
     * @unitTestDescription
     */
    @Test
    public void testValidate11() throws Exception {
        //arrange
        Account nickNameValid = new Account("test@example.com", "test", "swx", "ROLE_USER");
        when(accountServiceMock.findByNickname("swx")).thenReturn(nickNameValid);
        when(accountServiceMock.findByEmail("test@example.com")).thenReturn(nickNameValid);

        // act
        Account account = new Account();
        account.setEmail("summer@summer.com");
        account.setNickname("swx");
        Errors errors = new BeanPropertyBindingResult(account, "");
        accountSignUpValidator.validate(account, errors);

        // assert
        assertEquals(0,errors.getFieldErrorCount("email"));

        assertEquals(1,errors.getFieldErrorCount("nickname"));
        assertEquals("Nickname is already taken", errors.getFieldError("nickname").getDefaultMessage());

    }

    /**
     * @unitTestId
     * @unitTestTarget AccountSignUpValidator.validate
     * @unitTestType
     * @unitTestDescription
     */
    @Test
    public void testValidate12() throws Exception {
        //arrange
        Account nickNameValid = new Account("test@example.com", "test", "swx", "ROLE_USER");
        when(accountServiceMock.findByNickname("swx")).thenReturn(nickNameValid);
        when(accountServiceMock.findByEmail("test@example.com")).thenReturn(nickNameValid);

        // act
        Account account = new Account();
        account.setEmail("summer@summer.com");
        account.setNickname("summer");
        Errors errors = new BeanPropertyBindingResult(account, "");
        accountSignUpValidator.validate(account, errors);

        // assert
        assertEquals(0,errors.getFieldErrorCount("email"));

        assertEquals(0,errors.getFieldErrorCount("nickname"));


    }
}

