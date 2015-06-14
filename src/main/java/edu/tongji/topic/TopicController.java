package edu.tongji.topic;

import edu.tongji.error.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by Breezewish on 6/14/15.
 */

@Controller
public class TopicController {

    @Autowired
    TopicRepository topicRepository;

    @RequestMapping(value = "topics", method = RequestMethod.GET)
    public String listUnderTopic(Model model) {
        model.addAttribute("list", topicRepository.findAll());
        return "topic/list";
    }

}
