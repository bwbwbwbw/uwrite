package edu.tongji.article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

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


    @RequestMapping(value="article/list-{uid}",method = RequestMethod.GET)
    public String list(Model model,@PathVariable String uid)
    {
        Long p_uid=Long.parseLong(uid);
      model.addAttribute("list",articleRepository.listAll(p_uid));
        return "article/list";
    }
    @RequestMapping(value="article/delete-{id}",method = RequestMethod.GET)
    public String delete(@PathVariable Long id )
    {

     articleRepository.deleteById(id);

        return "redirect:article/list";
    }
    @RequestMapping(value="article/update",method = RequestMethod.POST)
    public String update(@RequestParam Long uid,@RequestParam Long id,@RequestParam String title,@RequestParam String markdown)
    {
       articleRepository.update(uid,id,title,markdown);
       return "article/article";
    }
}
