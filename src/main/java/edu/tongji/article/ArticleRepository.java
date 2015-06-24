package edu.tongji.article;

import edu.tongji.account.Account;
import edu.tongji.topic.Topic;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;

@Repository
@Transactional
public class ArticleRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Article save(Article article) {
        entityManager.persist(article);
        return article;
    }

    public List<Article> listAllArticle() {
        try {
            return entityManager.createNamedQuery(Article.FIND_ALL, Article.class)
                    .getResultList();
        } catch (PersistenceException e) {
            return null;
        }
    }

    public List<Article> listUserArticle(Account account) {
        try {
            return entityManager.createNamedQuery(Article.FIND_UNDER_USER, Article.class)
                    .setParameter("uid", account.getId())
                    .getResultList();
        } catch (PersistenceException e) {
            return null;
        }
    }

    public List<Article> listTopicArticle(Topic topic) {
        try {
            return entityManager.createNamedQuery(Article.FIND_UNDER_TOPIC, Article.class)
                    .setParameter("id", topic.getId())
                    .getResultList();
        } catch (PersistenceException e) {
            return null;
        }
    }

    public Article getArticle(Long id) {
        try {
            return entityManager.createNamedQuery(Article.FIND_BY_ID, Article.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (PersistenceException e) {
            return null;
        }
    }

    public Article getArticle(Account account, Long id) {
        try {
            return entityManager.createNamedQuery(Article.FIND_BY_UID_ID, Article.class)
                    .setParameter("uid", account.getId())
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (PersistenceException e) {
            return null;
        }
    }

    @Transactional
    public Boolean delete(Account account, Long id) {
        Article article = getArticle(account, id);
        if (article != null) {
            entityManager.remove(article);
        }
        return true;
    }

    @Transactional
    public Article update(Account account, Topic topic, Long id, String markdown, String title) {
        Article article = getArticle(account, id);
        if (article != null) {
            article.setTopic(topic);
            article.setMarkdown(markdown);
            article.setTitle(title);
            entityManager.merge(article);
        }
        return article;
    }
    public Boolean hasLiked(String email,Long id)
    {
        Article article=getArticle(id);

        return article.getLikedUser().contains(email);
    }
    public void like(String email,Long id)
    {
        Article article=getArticle(id);
        long likeNow=article.getLikes();
        article.setLikes(++likeNow);
        article.addLikedUser(email);
        entityManager.merge(article);
    }
}
