package edu.tongji.article;

import org.pegdown.PegDownProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Transient;

@Repository
@Transactional(readOnly = true)
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

}
