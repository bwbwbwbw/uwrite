package edu.tongji.article;

import edu.tongji.account.Account;
import edu.tongji.account.AccountRepository;
import edu.tongji.error.ResourceNotFoundException;
import edu.tongji.topic.Topic;
import edu.tongji.topic.TopicRepository;
import edu.tongji.topic.TopicService;
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
    private ArticleService articleService;


    @Autowired
    TopicService topicService;

    @RequestMapping(value = "article/view/user/{id}", method = RequestMethod.GET)
    public String listMine(Model model, @PathVariable("id") Long id) {
        model.addAttribute("list", articleService.listUserArticleByUid(id));
        return "article/list";
    }

    @RequestMapping(value = "article/view/all", method = RequestMethod.GET)
    public String listAll(Principal principal, Model model) {
        model.addAttribute("list", articleService.listAllArticle());
        return "article/list";
    }

    @RequestMapping(value = "article/view/{id}/{slug}")
    public String view(Model model, @PathVariable("id") Long id, @PathVariable String slug) {
        Article article = articleService.getArticleById(id);
        if (!slug.equals(article.getUrl())) {
            return "redirect:" + article.getFinalUrl();
        } else {
            model.addAttribute("article", article);
            return "article/view";
        }
    }

    @RequestMapping(value = "topic/{slug}", method = RequestMethod.GET)
    public String listUnderTopic(Model model, @PathVariable String slug) {
        model.addAttribute("list", articleService.listTopicArticleBySlug(slug));
        model.addAttribute("topiclist", topicService.listTopic());
        model.addAttribute("Slug",slug);
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
        return articleService.createArticle(principal.getName(), topicId, title, markdown);
    }

    @RequestMapping(value = "article/id/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public String delete(Principal principal, @PathVariable("id") Long id) {
        articleService.deleteArticle(principal.getName(), id);
        return "{}";
    }

    @RequestMapping(value = "article/id/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public Article update(Principal principal, @PathVariable("id") Long id, @RequestParam String title, @RequestParam String markdown, @RequestParam Long topicId) {
        return articleService.updateArticle(principal.getName(), id, topicId, title, markdown);
    }

    @RequestMapping(value = "article/like/{id}",method=RequestMethod.PUT)
    @ResponseBody
    public Boolean like(Principal principal,@PathVariable ("id") Long id)
    {
        String userEmail=principal.getName();
        if(articleService.hasLiked(userEmail,id))
        {
           return false;
        }

        articleService.like(userEmail,id);
        return true;
    }
    
}
