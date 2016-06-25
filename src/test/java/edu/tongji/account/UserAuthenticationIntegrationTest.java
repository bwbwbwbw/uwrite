package edu.tongji.account;

import edu.tongji.config.WebSecurityConfigurationAware;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class UserAuthenticationIntegrationTest extends WebSecurityConfigurationAware {
    private static String SEC_CONTEXT_ATTR = HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

    @Test
    public void userAuthenticates() throws Exception {
        mockMvc.perform(post("/authenticate").param("username", "admin").param("password", "admin"))
                .andExpect(r -> Assert.assertEquals("admin", ((SecurityContext) r.getRequest().getSession().getAttribute(SEC_CONTEXT_ATTR)).getAuthentication().getName()));
    }

    @Test
    public void userAuthenticationFails() throws Exception {
        mockMvc.perform(post("/authenticate").param("username", "admin").param("password", "123"))
                .andExpect(r -> Assert.assertNull(r.getRequest().getSession().getAttribute(SEC_CONTEXT_ATTR)));
    }
}