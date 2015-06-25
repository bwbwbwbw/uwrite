package edu.tongji.search;

import edu.tongji.article.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by pc-dll on 2015/6/25.
 */
public class SearchService {

    @Autowired
    ArticleService articleService;

    public SearchService() {

    }

}
