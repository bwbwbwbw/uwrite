package edu.tongji.article;

import edu.tongji.account.Account;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

@SuppressWarnings("serial")
@Entity
@EntityListeners(ArticleAdult.class)
@Table(name = "article")
@NamedQueries({
        @NamedQuery(name = Article.FIND_BY_UID_ID, query = "select a from Article a where a.id = :id and a.user.id = :uid"),
        @NamedQuery(name = Article.FIND_MINE, query = "select a from Article a where a.user.id = :uid order by a.createAt desc"),
})
public class Article implements java.io.Serializable {
    public static final String FIND_BY_UID_ID = "Article.findByUidId";
    public static final String FIND_MINE = "Article.findMine";
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String title;

    @Column
    @Type(type = "text")
    private String markdown;

    @Column
    @Type(type = "text")
    private String html;

    @Column
    private String url;

    @ManyToOne
    private Account user;

    @Column
    private Date createAt;

    @Column
    private Date updateAt;

    protected Article() {

    }

    public Article(Account owner, String title, String markdown) {
        this.user = owner;
        this.title = title;
        this.markdown = markdown;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMarkdown() {
        return markdown;
    }

    public void setMarkdown(String markdown) {
        this.markdown = markdown;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Account getUser() {
        return user;
    }

    public void setUser(Account user) {
        this.user = user;
    }
}
