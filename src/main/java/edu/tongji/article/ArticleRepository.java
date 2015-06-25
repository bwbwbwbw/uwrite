package edu.tongji.article;

import edu.tongji.account.Account;
import edu.tongji.topic.Topic;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private HtmlFilter htmlFilter;

    @Transactional
    public Article save(Article article) {
        article.setHtml(htmlFilter.filter(article.getHtml()));
        article.setBrief(htmlFilter.filter(article.getBrief()));
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
            article.setDeleted(true);
            entityManager.merge(article);
        }
        return true;
    }

    @Transactional
    public Article update(Account account, Topic topic, Long id, String html, String title, String coverImage, String brief) {
        Article article = getArticle(account, id);
        if (article != null) {
            article.setTopic(topic);
            article.setHtml(htmlFilter.filter(html));
            article.setTitle(title);
            article.setCoverImage(coverImage);
            article.setBrief(htmlFilter.filter(brief));
            entityManager.merge(article);
        }
        return article;
    }

    public Boolean hasLiked(Account account, Long id) {
        Article article = getArticle(id);
        List<Account> likeduser = article.getLikedUsers();
        for (Account a : likeduser) {
            if (a.getId().equals(account.getId()))
                return true;
        }
        return false;
    }

    public void like(Account account, Long id) {
        Article article = getArticle(id);
        List<Account> likeduser = article.getLikedUsers();
        likeduser.add(account);
        article.setLikedUsers(likeduser);
        entityManager.merge(article);
    }
}
