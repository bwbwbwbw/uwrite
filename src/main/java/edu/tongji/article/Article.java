package edu.tongji.article;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.tongji.account.Account;
import edu.tongji.slug.UrlSlugGenerator;
import edu.tongji.topic.Topic;
import org.hibernate.annotations.Type;
import org.springframework.beans.factory.annotation.Configurable;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
@Entity
@EntityListeners(ArticleAdult.class)
@Configurable
@Table(name = "article")
@NamedQueries({
        @NamedQuery(name = Article.FIND_BY_UID_ID, query = "select a from Article a where a.id = :id and a.user.id = :uid and a.deleted = false "),
        @NamedQuery(name = Article.FIND_BY_ID, query = "select a from Article a where a.id = :id and a.deleted = false"),
        @NamedQuery(name = Article.FIND_ALL, query = "select a from Article a where a.deleted = false order by a.createAt desc"),
        @NamedQuery(name = Article.FIND_UNDER_USER, query = "select a from Article a where a.user.id = :uid and a.deleted = false order by a.createAt desc"),
        @NamedQuery(name = Article.FIND_UNDER_TOPIC, query = "select a from Article a where a.topic.id = :id and a.deleted = false order by a.createAt desc"),
})
public class Article implements java.io.Serializable {
    public static final String FIND_BY_ID = "Article.findById";
    public static final String FIND_BY_UID_ID = "Article.findByUidId";
    public static final String FIND_ALL = "Article.findAll";
    public static final String FIND_UNDER_USER = "Article.findMine";
    public static final String FIND_UNDER_TOPIC = "Article.findUnderTopic";

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String title;

    @Column
    @Type(type = "text")
    private String html;

    @Column
    private String url;

    @ManyToOne(cascade = CascadeType.REMOVE)
    private Account user;

    @Column
    private Date createAt;

    @Column
    private Date updateAt;

    @ManyToOne(cascade = CascadeType.REMOVE)
    private Topic topic;

    @ManyToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinTable(name = "likes",
            joinColumns = {@JoinColumn(name = "ARTICLE_ID")},
            inverseJoinColumns = {@JoinColumn(name = "ACCOUNT_ID")})
    private List<Account> likedUsers;

    @Column
    private String coverImage;

    @Column
    @Type(type = "text")
    private String brief;

    @Column
    private Boolean deleted;

    protected Article() {
    }

    public Article(Account owner, Topic topic, String title, String html, String coverImage, String brief) {
        this.setUser(owner);
        this.setTitle(title);
        this.setHtml(html);
        this.setTopic(topic);
        this.setCoverImage(coverImage);
        this.setBrief(brief);
        this.deleted = false;
    }

    public void setTitle(String title) {
        this.title = title;
        this.url = new UrlSlugGenerator().toSlug(title);
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getTitle() {
        return title;
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

    public String getFinalUrl() {
        return "/article/view/" + getId() + "/" + getUrl();
    }

    public String getEditUrl() {
        return "/article/edit/" + getId();
    }

    public List<Account> getLikedUsers() {
        return likedUsers;
    }

    public void setLikedUsers(List<Account> likedUser) {
        this.likedUsers = likedUser;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}
