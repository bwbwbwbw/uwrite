package edu.tongji.account;

import edu.tongji.config.WebSecurityConfigurationAware;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * @testType INTEGRATION_TEST
 */
public class UserAuthenticationIntegrationTest extends WebSecurityConfigurationAware {
    private static String SEC_CONTEXT_ATTR = HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

    @Test
    public void userAuthenticates() throws Exception {
        MvcResult result = mockMvc.perform(post("/authenticate").param("username", "test@example.com").param("password", "test"))
                .andExpect(r -> assertEquals("test@example.com", ((SecurityContext) r.getRequest().getSession().getAttribute(SEC_CONTEXT_ATTR)).getAuthentication().getName()))
                .andReturn();
        assertEquals("{\"success\": true}", result.getResponse().getContentAsString());
    }

    @Test
    public void userAuthenticationInvalidEmail() throws Exception {
        MvcResult result = mockMvc.perform(post("/authenticate").param("username", "dummy").param("password", "dummy"))
                .andExpect(r -> Assert.assertNull(r.getRequest().getSession().getAttribute(SEC_CONTEXT_ATTR)))
                .andReturn();
        assertEquals("{\"success\": false}", result.getResponse().getContentAsString());
    }

    @Test
    public void userAuthenticationNoSuchUser() throws Exception {
        MvcResult result = mockMvc.perform(post("/authenticate").param("username", "dummy@example.com").param("password", "dummy"))
                .andExpect(r -> Assert.assertNull(r.getRequest().getSession().getAttribute(SEC_CONTEXT_ATTR)))
                .andReturn();
        assertEquals("{\"success\": false}", result.getResponse().getContentAsString());
    }

    @Test
    public void userAuthenticationWrongPassword() throws Exception {
        MvcResult result = mockMvc.perform(post("/authenticate").param("username", "test@example.com").param("password", "123"))
                .andExpect(r -> Assert.assertNull(r.getRequest().getSession().getAttribute(SEC_CONTEXT_ATTR)))
                .andReturn();
        assertEquals("{\"success\": false}", result.getResponse().getContentAsString());
    }

}