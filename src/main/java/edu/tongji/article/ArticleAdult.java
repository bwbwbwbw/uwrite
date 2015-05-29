package edu.tongji.article;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;

public class ArticleAdult {

    @PrePersist
    public void prePersist(Article article) {
        article.setCreateAt(new Date());
    }

    @PreUpdate
    public void preUpdate(Article article) {
        article.setUpdateAt(new Date());
    }

}
