package edu.tongji.article;

import org.pegdown.PegDownProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.management.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Transient;
import java.util.List;

@Repository
//@Transactional(readOnly = true)
public class ArticleRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    @Transient
    private PegDownProcessor pegDownProcessor;

    @Transactional
    public Article save(Article article) {
        article.setHtml(pegDownProcessor.markdownToHtml(article.getMarkdown()));
        article.setUrl(article.getTitle());
        entityManager.persist(article);
        return article;
    }

   public List<Article> listAll(Long uid){
     try {
         return entityManager.createNamedQuery(Article.FIND_ALL_ARTICLE, Article.class).setParameter("uid",uid)
                 .getResultList();
     }catch (PersistenceException e)
     {
         return null;
     }
   }
    @Transactional
    public void deleteById(Long id)
    {

    entityManager.createNamedQuery(Article.DELETE_BY_ID)
                 .setParameter("id",id)
                 .executeUpdate();


    }

    @Transactional
    public void update(Long uid,Long id ,String markdown,String title)
    {
entityManager.createNamedQuery(Article.UPDATE)
        .setParameter("uid",uid)
        .setParameter("id",id)
        .setParameter("markdown",markdown)
        .setParameter("title",title)
        .executeUpdate();

    }
}
