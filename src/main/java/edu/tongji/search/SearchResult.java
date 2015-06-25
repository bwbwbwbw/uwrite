package edu.tongji.search;

import java.util.Date;

/**
 * Created by pc-dll on 2015/6/25.
 */
public class SearchResult {
    public Long id;
    public Date createAt;
    public Date updateAt;
    public String title;
    public String url;
    public String html;
    public Long topic_id;
    public String brief;

    public SearchResult() {

    }

    public SearchResult(Long id, Date createAt, Date updateAt, String title,
                        String url, String html, Long topic_id, String brief) {
        this.id = id;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.title = title;
        this.url = url;
        this.html = html;
        this.topic_id = topic_id;
        this.brief = brief;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public Long getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(Long topic_id) {
        this.topic_id = topic_id;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
