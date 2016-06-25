package edu.tongji.topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    public List<Topic> listTopic() {
        return topicRepository.findAll();
    }

}
