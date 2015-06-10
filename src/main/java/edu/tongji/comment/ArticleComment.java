package edu.tongji.comment;

import edu.tongji.account.Account;
import edu.tongji.article.Article;

import javax.persistence.*;

/**
 * Created by Breezewish on 6/9/15.
 */

@SuppressWarnings("serial")
@Entity
@NamedQueries({
        @NamedQuery(name = ArticleComment.FIND_BY_ARTICLE, query = "select c from ArticleComment c where c.article.id = :article_id order by c.createAt asc"),
})
public class ArticleComment extends Comment {

    public static final String FIND_BY_ARTICLE = "ArticleComment.findByArticle";

    @ManyToOne(fetch = FetchType.LAZY)
    private Article article;

    public ArticleComment(Article source, Account user, String markdown) {
        this.setMarkdown(markdown);
        this.setArticle(source);
        this.setUser(user);
    }

    protected ArticleComment() {
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
}
