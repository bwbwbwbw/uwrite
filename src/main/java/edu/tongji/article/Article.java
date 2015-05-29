package edu.tongji.article;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

@SuppressWarnings("serial")
@Entity
@EntityListeners(ArticleAdult.class)
@Table(name = "article")
public class Article implements java.io.Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String title;

    @Column
    @Type(type="text")
    private String markdown;

    @Column
    @Type(type="text")
    private String html;

    @Column
    private String url;

    @Column
    private Long uid;

    @Column
    private Date createAt;

    @Column
    private Date updateAt;

    protected Article() {

    }

    public Article(Long uid, String title, String markdown) {
        this.uid = uid;
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

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
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

}
