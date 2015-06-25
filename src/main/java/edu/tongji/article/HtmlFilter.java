package edu.tongji.article;

import org.owasp.validator.html.AntiSamy;
import org.owasp.validator.html.CleanResults;
import org.owasp.validator.html.Policy;
import org.springframework.core.io.ClassPathResource;

/**
 * Created by Breezewish on 6/25/15.
 */
public class HtmlFilter {

    private AntiSamy antiSamy;
    private Policy policy;

    public HtmlFilter() {
        try {
            policy = Policy.getInstance(new ClassPathResource("antisamy-myspace-1.4.4.xml").getFile());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        antiSamy = new AntiSamy();
    }

    public String filter(String html) {
        try {
            CleanResults result = antiSamy.scan(html, policy);
            return result.getCleanHTML();
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

}
