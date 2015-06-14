package edu.tongji.topic;

import edu.tongji.article.Article;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;

/**
 * Created by pc-dll on 2015/6/10.
 */
@Repository
public class TopicRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public List<Topic> findAll() {
        try {
            return entityManager.createNamedQuery(Topic.FIND_ALL, Topic.class)
                    .getResultList();
        } catch (PersistenceException e) {
            return null;
        }
    }

    public Topic findById(Long id) {
        try {
            return entityManager.createNamedQuery(Topic.FIND_BY_ID, Topic.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (PersistenceException e) {
            return null;
        }
    }

    public Topic findBySlug(String slug) {
        try {
            return entityManager.createNamedQuery(Topic.FIND_BY_SLUG, Topic.class)
                    .setParameter("slug", slug)
                    .getSingleResult();
        } catch (PersistenceException e) {
            return null;
        }
    }

}
