package edu.tongji.article;

import edu.tongji.account.Account;

/**
 * Created by Breezewish on 6/26/15.
 */
public class ArticleSearchItem implements java.io.Serializable {

    public Long id;

    public String title;

    public String html;

    public String url;

    public Account user;

    public String brief;

    protected ArticleSearchItem() {}

    public ArticleSearchItem(Article article)
    {
        this.id = article.getId();
        this.title = article.getTitle();
        this.html = article.getHtml();
        this.url = article.getUrl();
        this.user = article.getUser();
        this.brief = article.getBrief();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Account getUser() {
        return user;
    }

    public void setUser(Account user) {
        this.user = user;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getFinalUrl() {
        return "/article/view/" + getId() + "/" + getUrl();
    }

}
