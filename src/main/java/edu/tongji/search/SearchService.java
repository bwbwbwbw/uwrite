package edu.tongji.search;

import edu.tongji.article.Article;
import edu.tongji.article.ArticleSearchItem;
import edu.tongji.article.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SearchService {

    @Autowired
    ArticleService articleService;

    private SearchClient searchClient;

    public SearchService() {
        searchClient = new SearchClient();
    }

    public void addAll() {
        searchClient.resetIndex();
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

    public List<ArticleSearchItem> search(String keyword) {
        return searchClient.search(keyword);
    }

}
