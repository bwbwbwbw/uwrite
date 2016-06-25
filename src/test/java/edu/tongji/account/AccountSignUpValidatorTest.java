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
     * @unitTestDescription 输入一个为Account实例的对象,预期返回true
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
     * @unitTestDescription 输入一个不为Account实例的对象,预期返回false
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
     * @unitTestDescription 输入一个null,预期返回false
     */
    @Test
    public void testSupports3() throws Exception {
        assertEquals(false, accountSignUpValidator.supports(null));
    }

    /**
     * @unitTestId TEST_SIGN_UP_VALIDATOR_4
     * @unitTestTarget AccountSignUpValidator.validate
     * @unitTestType 等价类测试
     * @unitTestDescription 输入的email和nickname都为空的情况,预期出现email和nickname错误
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
     * @unitTestDescription 输入一个email为空,nickname不为空但被占用的情况,预期出现email错误
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
     * @unitTestId TEST_SIGN_UP_VALIDATOR_6
     * @unitTestTarget AccountSignUpValidator.validate
     * @unitTestType 等价类测试
     * @unitTestDescription 输入一个email为空,nickname合法的情况,预期返回email错误
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
     * @unitTestId TEST_SIGN_UP_VALIDATOR_7
     * @unitTestTarget AccountSignUpValidator.validate
     * @unitTestType 等价类测试
     * @unitTestDescription 输入一个email合法且未被占用,nickname为空的情况,预期返回nickname错误
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
     * @unitTestId TEST_SIGN_UP_VALIDATOR_8
     * @unitTestTarget AccountSignUpValidator.validate
     * @unitTestType 等价类测试
     * @unitTestDescription 输入一个email为空,nickname已被占用的情况,预期返回nickname错误
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
     * @unitTestId TEST_SIGN_UP_VALIDATOR_9
     * @unitTestTarget AccountSignUpValidator.validate
     * @unitTestType 等价类测试
     * @unitTestDescription 输入一个email不合法,nickname合法的情况,预期返回email错误
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
     * @unitTestId TEST_SIGN_UP_VALIDATOR_10
     * @unitTestTarget AccountSignUpValidator.validate
     * @unitTestType 等价类测试
     * @unitTestDescription 输入一个email被占用,nickname为空的情况,预期返回email错误和nickname错误
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
     * @unitTestId TEST_SIGN_UP_VALIDATOR_11
     * @unitTestTarget AccountSignUpValidator.validate
     * @unitTestType 等价类测试
     * @unitTestDescription 输入一个email被占用,nickname被占用的情况,预期返回email错误和nickname错误
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
     * @unitTestId TEST_SIGN_UP_VALIDATOR_12
     * @unitTestTarget AccountSignUpValidator.validate
     * @unitTestType 等价类测试
     * @unitTestDescription 输入一个email被占用,nickname合法的情况,预期返回email错误
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
     * @unitTestId TEST_SIGN_UP_VALIDATOR_13
     * @unitTestTarget AccountSignUpValidator.validate
     * @unitTestType 等价类测试
     * @unitTestDescription 输入一个email合法且未被占用,nickname为空的情况,预期返回nickname错误
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
     * @unitTestId TEST_SIGN_UP_VALIDATOR_14
     * @unitTestTarget AccountSignUpValidator.validate
     * @unitTestType 等价类测试
     * @unitTestDescription 输入一个email合法且未被占用,nickname被占用的情况,预期返回nickname错误
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
     * @unitTestId TEST_SIGN_UP_VALIDATOR_15
     * @unitTestTarget AccountSignUpValidator.validate
     * @unitTestType 等价类测试
     * @unitTestDescription 输入一个email合法且未被占用,nickname未被占用的情况,预期执行结果正确,不返回任何错误
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

