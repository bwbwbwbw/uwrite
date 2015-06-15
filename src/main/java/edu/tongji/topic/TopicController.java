package edu.tongji.topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Breezewish on 6/14/15.
 */

@Controller
public class TopicController {

    @Autowired
    TopicService topicService;

    @RequestMapping(value = "topics", method = RequestMethod.GET)
    public String listTopics(Model model) {
        model.addAttribute("list", topicService.listTopic());
        return "topic/list";
    }

}
