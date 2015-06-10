package edu.tongji.comment;

import edu.tongji.account.Account;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

@SuppressWarnings("serial")
@Entity
@EntityListeners(CommentAdult.class)
@Table(name = "comment")
@NamedQueries({
        @NamedQuery(name = Comment.FIND_BY_UID_ID, query = "select a from Article a where a.id = :id and a.user.id = :uid"),
})
public class Comment implements java.io.Serializable {

    public static final String FIND_BY_UID_ID = "Comment.findByUidId";

    @Id
    @GeneratedValue
    private Long id;

    @Column
    @Type(type = "text")
    private String markdown;

    @Column
    @Type(type = "text")
    private String html;

    @ManyToOne
    private Account user;

    @Column
    private Date createAt;

    protected Comment() {

    }

    public Long getId() {
        return id;
    }

    public String getMarkdown() {
        return markdown;
    }

    public void setMarkdown(String markdown) {
        this.markdown = markdown;
        this.html = markdown;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public Account getUser() {
        return user;
    }

    public void setUser(Account user) {
        this.user = user;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
}
