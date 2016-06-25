package edu.tongji.account;

import edu.tongji.config.WebAppConfigurationAware;
import org.json.JSONObject;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * @testType INTEGRATION_TEST
 */
public class UserSignUpIntegrationTest extends WebAppConfigurationAware {

    @Test
    public void userSignUp() throws Exception {
        MockHttpServletResponse resp = mockMvc.perform(
                post("/signup")
                        .param("email", "newuser@example.com")
                        .param("password", "admin")
                        .param("nickname", "newuser")
        )
                .andReturn()
                .getResponse();
        assertEquals(200, resp.getStatus());
        JSONAssert.assertEquals(
                "{\"id\":2,\"email\":\"newuser@example.com\",\"nickname\":\"newuser\",\"avatar\":\"default.png\",\"role\":\"ROLE_USER\",\"link\":\"/article/view/user/2\",\"avatarLink\":\"/image/default.png\"}",
                new JSONObject(resp.getContentAsString()),
                false
        );
    }

    @Test
    public void userSignUpInvalidEmail() throws Exception {
        MockHttpServletResponse resp = mockMvc.perform(
                post("/signup")
                        .param("email", "test")
                        .param("password", "dummy")
                        .param("nickname", "test")
        )
                .andReturn()
                .getResponse();
        assertEquals(400, resp.getStatus());
        JSONAssert.assertEquals(
                "{\"error\":true,\"type\":\"error.constraint\",\"message\":\"The value must be a valid email!\"}",
                new JSONObject(resp.getContentAsString()),
                false
        );
    }

    @Test
    public void userSignUpDuplicateEmail() throws Exception {
        MockHttpServletResponse resp = mockMvc.perform(
                post("/signup")
                        .param("email", "test@example.com")
                        .param("password", "dummy")
                        .param("nickname", "newuser")
        )
                .andReturn()
                .getResponse();
        assertEquals(400, resp.getStatus());
        JSONAssert.assertEquals(
                "{\"error\":true,\"type\":\"error.constraint\",\"message\":\"Email is already taken\"}",
                new JSONObject(resp.getContentAsString()),
                false
        );
    }

    @Test
    public void userSignUpDuplicateNickname() throws Exception {
        MockHttpServletResponse resp = mockMvc.perform(
                post("/signup")
                        .param("email", "newuser@example.com")
                        .param("password", "dummy")
                        .param("nickname", "testaccount")
        )
                .andReturn()
                .getResponse();
        assertEquals(400, resp.getStatus());
        JSONAssert.assertEquals(
                "{\"error\":true,\"type\":\"error.constraint\",\"message\":\"Nickname is already taken\"}",
                new JSONObject(resp.getContentAsString()),
                false
        );
    }

}
