package edu.tongji.topic;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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



   public Topic findById(Long id)
   {
       try {
           return entityManager.createNamedQuery(Topic.FIND_BY_ID, Topic.class)
                   .setParameter("id", id)
                   .getSingleResult();
       }catch (PersistenceException e)
       {
           return  null;
       }
   }
}
