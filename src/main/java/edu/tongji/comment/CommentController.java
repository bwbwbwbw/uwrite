package edu.tongji.comment;

import edu.tongji.account.Account;
import edu.tongji.account.AccountRepository;
import edu.tongji.article.Article;
import edu.tongji.article.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * Created by summer on 6/11/15.
 */
@Controller
@Secured("ROLE_USER")
public class CommentController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CommentRepository commentRepository;

    @RequestMapping(value = "article/comment", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public Comment create(Principal principal, @RequestParam String markdown,
                          @RequestParam Long articleId) {
        Account account = accountRepository.findByEmail(principal.getName());
        Article article = articleRepository.getArticle(articleId);
        ArticleComment comment = new ArticleComment(article, account, markdown);
        commentRepository.save(comment);
        return comment;
    }

}
