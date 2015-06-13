package edu.tongji.topic;

import javax.persistence.*;

/**
 * Created by pc-dll on 2015/6/9.
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "topic")
@NamedQueries({@NamedQuery(name = Topic.FIND_BY_ID, query = "select t from Topic t where t.id= :id"),})
public class Topic implements java.io.Serializable {

    public static final String FIND_BY_ID = "Topic.findById";


    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String topicName;

    @Column
    private String description;

    @Column
    private String topicPicture;

    public Topic() {

    }

    public Topic(String topicName, String description, String topicPicture) {
        this.description = description;
        this.topicName = topicName;
        this.topicPicture = topicPicture;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTopicPicture() {
        return topicPicture;
    }

    public void setTopicPicture(String topicPicture) {
        this.topicPicture = topicPicture;
    }

    public Long getId() {
        return id;
    }


}
