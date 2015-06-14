package edu.tongji.topic;

import org.hibernate.annotations.Type;

import javax.persistence.*;

/**
 * Created by pc-dll on 2015/6/9.
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "topic")
@NamedQueries({
        @NamedQuery(name = Topic.FIND_ALL, query = "select t from Topic t order by t.id asc"),
        @NamedQuery(name = Topic.FIND_BY_ID, query = "select t from Topic t where t.id= :id"),
        @NamedQuery(name = Topic.FIND_BY_SLUG, query = "select t from Topic t where t.slug= :slug"),
})
public class Topic implements java.io.Serializable {

    public static final String FIND_ALL = "Topic.findAll";
    public static final String FIND_BY_ID = "Topic.findById";
    public static final String FIND_BY_SLUG = "Topic.findBySlug";

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String name;

    @Column
    @Type(type = "text")
    private String description;

    @Column
    private String slug;

    public Topic() {

    }

    public Topic(String name, String description) {
        this.setName(name);
        this.setDescription(description);
    }

    public String getName() {
        return name;
    }

    public void setName(String topicName) {
        this.name = topicName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getFinalUrl() {
        return "/topic/" + this.getSlug();
    }

}
