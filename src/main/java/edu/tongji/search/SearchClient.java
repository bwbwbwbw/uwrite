package edu.tongji.search;

import edu.tongji.article.Article;
import edu.tongji.article.ArticleSearchItem;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

import java.io.IOException;
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
        try {
            client.admin().indices().delete(new DeleteIndexRequest("uwrite")).actionGet();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void initIndexData(List<Article> articles) {
        for (Article article : articles) {
            addData(article);
        }
        client.admin().indices().prepareRefresh().execute().actionGet();
    }

    public void addData(Article article) {
        ObjectWriter ow = new ObjectMapper().writer();
        try {
            String json = ow.writeValueAsString(new ArticleSearchItem(article));
            client.prepareIndex(esIndex, esType)
                    .setSource(json)
                    .execute()
                    .actionGet();
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateData(Article article) {
        ObjectWriter ow = new ObjectMapper().writer();
        try {
            client.prepareUpdate(esIndex, esType, article.getId().toString())
                    .setDoc(ow.writeValueAsString(new ArticleSearchItem(article)))
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void deleteData(Article article) {
        client.prepareDelete(esIndex, esType, article.getId().toString())
                .execute()
                .actionGet();
    }

    public List<ArticleSearchItem> search(String keyword) {
        QueryBuilder qb = new MultiMatchQueryBuilder(keyword, "title", "html");
        List<ArticleSearchItem> list = new ArrayList<>();
        SearchResponse searchResponse = client
                .prepareSearch(esIndex)
                .setTypes(esType)
                .setQuery(qb)
                .setSize(50)
                .execute()
                .actionGet();
        SearchHits hits = searchResponse.getHits();
        SearchHit[] resultHits = hits.getHits();

        ObjectMapper mapper = new ObjectMapper();

        try {
            for (SearchHit hit : resultHits) {
                list.add(mapper.readValue(hit.getSourceAsString(), ArticleSearchItem.class));
            }
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
