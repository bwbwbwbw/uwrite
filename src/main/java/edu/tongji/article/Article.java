package edu.tongji.article;

import edu.tongji.account.Account;
import edu.tongji.topic.Topic;
import org.hibernate.annotations.Type;
import org.pegdown.PegDownProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import javax.persistence.*;
import java.util.Date;

@SuppressWarnings("serial")
@Entity
@EntityListeners(ArticleAdult.class)
@Configurable
@Table(name = "article")
@NamedQueries({
        @NamedQuery(name = Article.FIND_BY_UID_ID, query = "select a from Article a where a.id = :id and a.user.id = :uid"),
        @NamedQuery(name = Article.FIND_MINE, query = "select a from Article a where a.user.id = :uid order by a.createAt desc"),
        @NamedQuery(name = Article.FIND_UNDER_TOPIC, query = "select a from Article a where a.topic.id= :id order by a.createAt desc"),
})
public class Article implements java.io.Serializable {
    public static final String FIND_BY_UID_ID = "Article.findByUidId";
    public static final String FIND_MINE = "Article.findMine";
    public static final String FIND_UNDER_TOPIC ="Article.findUnderTopic";
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

    @Autowired
    @Transient
    private PegDownProcessor pegDownProcessor;
    @ManyToOne
    private Topic topic;


    protected Article() {
    }

    public Article(Account owner, Topic topic, String title, String markdown) {
        this.setUser(owner);
        this.setTitle(title);
        this.setMarkdown(markdown);
        this.setTopic(topic);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        this.url = title;
        // TODO: process title
    }

    public String getMarkdown() {
        return markdown;
    }

    public void setMarkdown(String markdown) {
        this.markdown = markdown;
        this.html = markdown;
        // TODO: use pegdown processor
    }

    public String getHtml() {
        return html;
    }

    public String getUrl() {
        return url;
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
    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }
}
