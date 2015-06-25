package edu.tongji.comment;

import edu.tongji.account.Account;
import edu.tongji.article.Article;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;

@Repository
@Transactional
public class CommentRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public ArticleComment save(ArticleComment comment) {
        entityManager.persist(comment);
        return comment;
    }

    public List<ArticleComment> listAll(Article article) {
        try {
            return entityManager.createNamedQuery(ArticleComment.FIND_BY_ARTICLE, ArticleComment.class)
                    .setParameter("article_id", article.getId())
                    .getResultList();

        } catch (PersistenceException e) {
            return null;
        }
    }

    public Comment getComment(Account account, Long id) {
        try {
            return entityManager.createNamedQuery(Comment.FIND_BY_UID_ID, Comment.class)
                    .setParameter("uid", account.getId())
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (PersistenceException e) {
            return null;
        }
    }

    @Transactional
    public Boolean delete(Account account, Long id) {
        Comment comment = getComment(account, id);
        if (comment != null) {
            entityManager.remove(comment);
        }
        return true;
    }
}
