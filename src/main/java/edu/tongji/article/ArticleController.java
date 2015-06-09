package edu.tongji.article;

import edu.tongji.account.Account;
import edu.tongji.account.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * Created by Breezewish on 5/29/15.
 */
@Controller
@Secured("ROLE_USER")
public class ArticleController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ArticleRepository articleRepository;


    @RequestMapping(value = "article/create", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public Article create(Principal principal, @RequestParam String title, @RequestParam String markdown) {
        Account account = accountRepository.findByEmail(principal.getName());
        Article article = new Article(account, title, markdown);
        articleRepository.save(article);
        return article;
    }

    @RequestMapping(value = "article/create", method = RequestMethod.GET)
    public String create(Principal principal) {
        return "article/create";
    }

    @RequestMapping(value = "article/my", method = RequestMethod.GET)
    public String list(Principal principal, Model model) {
        Account account = accountRepository.findByEmail(principal.getName());
        model.addAttribute("list", articleRepository.listAll(account));
        return "article/list";
    }

    @RequestMapping(value = "article/id/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public String delete(Principal principal, @PathVariable("id") Long id) {
        Account account = accountRepository.findByEmail(principal.getName());
        if (account != null) {
            articleRepository.delete(account, id);
        }
        return "{}";
    }

    @RequestMapping(value = "article/id/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public Article update(Principal principal, @PathVariable("id") Long id, @RequestParam String title, @RequestParam String markdown) {
        Account account = accountRepository.findByEmail(principal.getName());
        if (account != null) {
            return articleRepository.update(account, id, title, markdown);
        } else {
            return null;
        }
    }
}
