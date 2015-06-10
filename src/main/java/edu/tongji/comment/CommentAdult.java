package edu.tongji.comment;

import javax.persistence.PrePersist;
import java.util.Date;

/**
 * Created by Breezewish on 6/9/15.
 */
public class CommentAdult {

    @PrePersist
    public void prePersist(Comment comment) {
        comment.setCreateAt(new Date());
    }

}
