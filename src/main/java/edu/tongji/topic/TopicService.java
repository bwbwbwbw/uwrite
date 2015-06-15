package edu.tongji.topic;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Breezewish on 6/15/15.
 */
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    public List<Topic> listTopic() {
        return topicRepository.findAll();
    }

}
