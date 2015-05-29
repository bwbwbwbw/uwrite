package edu.tongji.article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Breezewish on 5/29/15.
 */
@Controller
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;

    @RequestMapping(value = "article/create", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public Article create(@RequestParam Long uid, @RequestParam String title, @RequestParam String markdown) {
        Article article = new Article(uid, title, markdown);
        articleRepository.save(article);
        return article;
    }

}
