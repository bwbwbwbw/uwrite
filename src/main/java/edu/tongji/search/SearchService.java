package edu.tongji.search;

import edu.tongji.article.Article;
import edu.tongji.article.ArticleSearchItem;
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
    }

    public void addAll() {
        searchClient.resetIndex();
        System.out.print("Indexing initial data...");
        searchClient.initIndexData(articleService.listAllArticle());
        System.out.print("Index done.");
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

    public List<ArticleSearchItem> search(String keyword) {
        return searchClient.search(keyword);
    }

}
