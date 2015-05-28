package edu.tongji.error;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Breezewish on 5/28/15.
 */
public class ajaxAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        response.setHeader("content-type", "application/json");
        response.getWriter().print("{\"success\": true}");
    }

}
