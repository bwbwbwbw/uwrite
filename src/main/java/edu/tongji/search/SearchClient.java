package edu.tongji.search;

import com.alibaba.fastjson.JSON;
import edu.tongji.article.Article;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.flush.FlushRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

import java.util.ArrayList;
import java.util.List;

public class SearchClient {

    private Client client;

    private String esIndex = "uwrite";
    private String esType = "article";

    public SearchClient() {
        client = new TransportClient()
                .addTransportAddress(new InetSocketTransportAddress("127.0.0.1", 9300));
    }

    public void resetIndex() {
        client.admin().indices().delete(new DeleteIndexRequest("uwrite")).actionGet();
    }

    public void initIndexData(List<Article> articles) {
        for (Article article : articles) {
            addData(article);
        }
        client.admin().indices().flush(new FlushRequest("uwrite").force(true)).actionGet();
    }

    public void addData(Article article) {
        String json = JSON.toJSONString(article);
        client.prepareIndex(esIndex, esType)
                .setSource(json)
                .execute()
                .actionGet();
    }

    public void updateData(Article article) {

    }

    public void deleteData(Article article) {

    }

    public List<Article> search(String keyword) {
        QueryBuilder qb = new MultiMatchQueryBuilder(
                keyword,
                "title", "html"
        );
        List<Article> list = new ArrayList<Article>();
        SearchResponse searchResponse = client
                .prepareSearch(esIndex)
                .setTypes(esType)
                .setQuery(qb)
                .execute()
                .actionGet();
        SearchHits hits = searchResponse.getHits();
        System.out.println("查询到记录数=" + hits.getTotalHits());
        SearchHit[] searchHists = hits.getHits();
        if (searchHists.length > 0) {
            for (SearchHit hit : searchHists) {
                list.add(JSON.parseObject(hit.getSourceAsString(), Article.class));
            }
        }
        return list;
    }
}
