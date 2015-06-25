package edu.tongji.comment;

import edu.tongji.article.ArticleService;
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
    private ArticleService articleService;

    @RequestMapping(value = "article/comment", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public Comment create(Principal principal, @RequestParam String markdown,
                          @RequestParam Long articleId) {
        return articleService.addComment(principal.getName(), articleId, markdown);
    }



}
