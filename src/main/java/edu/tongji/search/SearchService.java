package edu.tongji.search;

import edu.tongji.article.Article;
import edu.tongji.article.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by pc-dll on 2015/6/25.
 */
public class SearchService {

    @Autowired
    ArticleService articleService;

    private SearchClient searchClient;

    public SearchService() {
        searchClient = new SearchClient();
        searchClient.initIndexData(articleService.listAllArticle());
    }

    public void add(Article article) {
        searchClient.addData(article);
    }

    public void update(Article article) {
        searchClient.updateData(article);
    }

    public void delete(Article article) {
        searchClient.deleteData(article);
    }

    public List<Article> search(String keyword) {
        return searchClient.search(keyword);
    }

}
