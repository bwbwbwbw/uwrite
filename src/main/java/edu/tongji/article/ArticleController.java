package edu.tongji.article;

import edu.tongji.account.Account;
import edu.tongji.account.AccountRepository;
import edu.tongji.error.ResourceNotFoundException;
import edu.tongji.topic.Topic;
import edu.tongji.topic.TopicRepository;
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

    @Autowired
    private TopicRepository topicRepository;

    @RequestMapping(value = "article/view/user/{id}", method = RequestMethod.GET)
    public String listMine(Model model, @PathVariable("id") Long id) {
        Account account = accountRepository.findById(id);
        model.addAttribute("list", articleRepository.listUserArticle(account));
        return "article/list";
    }

    @RequestMapping(value = "article/view/all", method = RequestMethod.GET)
    public String listAll(Principal principal, Model model) {
        model.addAttribute("list", articleRepository.listAllArticle());
        return "article/list";
    }

    @RequestMapping(value = "article/view/{id}/{slug}")
    public String view(Model model, @PathVariable("id") Long id, @PathVariable String slug) {
        Article article = articleRepository.getArticle(id);
        if (article == null) {
            throw new ResourceNotFoundException();
        }
        if (!slug.equals(article.getUrl())) {
            return "redirect:" + article.getFinalUrl();
        } else {
            model.addAttribute("article", article);
            return "article/view";
        }
    }

    @RequestMapping(value = "topic/{slug}", method = RequestMethod.GET)
    public String listUnderTopic(Model model, @PathVariable String slug) {
        Topic topic = topicRepository.findBySlug(slug);
        if (topic == null) {
            throw new ResourceNotFoundException();
        }
        model.addAttribute("list", articleRepository.listTopicArticle(topic));
        return "article/list";
    }

    @RequestMapping(value = "article/create", method = RequestMethod.GET)
    public String create() {
        return "article/create";
    }

    @RequestMapping(value = "article/create", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public Article create(Principal principal, @RequestParam String title, @RequestParam String markdown,
                          @RequestParam Long topicId) {
        Account account = accountRepository.findByEmail(principal.getName());
        Topic topic = topicRepository.findById(topicId);
        Article article = new Article(account, topic, title, markdown);
        articleRepository.save(article);
        return article;
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
    public Article update(Principal principal, @PathVariable("id") Long id, @RequestParam String title, @RequestParam String markdown, @RequestParam Long topicId) {
        Account account = accountRepository.findByEmail(principal.getName());
        Topic topic = topicRepository.findById(topicId);
        if (account != null) {
            return articleRepository.update(account, topic, id, title, markdown);
        } else {
            return null;
        }
    }
}
