package edu.tongji.search;

import edu.tongji.article.Article;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Search {

    private Client client;

    private String esIndex = "uwrite";
    private String esType = "article";

    public Search() {
        client = new TransportClient()
                .addTransportAddress(new InetSocketTransportAddress("127.0.0.1", 9300));
    }

    public void resetIndex()
    {

    }

    public void initIndexData(List<Article> articles)
    {
        List<String> jsondata = new ArrayList<>();
        for (Article article : articles) {
            jsondata.add((article));
        }

        createIndexResponse(esIndex, esType, jsondata);
    }

    public void addData(Article article)
    {
        String json = JsonUtil.obj2JsonData(article);
        createIndexResponse(esIndex, esType, json);
    }

    public void updateData(Article article)
    {

    }

    public void deleteData(Article article)
    {

    }

    private void createIndexResponse(String indexname, String type, List<String> jsondata) {
        IndexRequestBuilder requestBuilder = client.prepareIndex(indexname, type)
                .setRefresh(true);
        for (int i = 0; i < jsondata.size(); i++) {
            requestBuilder.setSource(jsondata.get(i)).execute().actionGet();
        }
    }

    private IndexResponse createIndexResponse(String indexname, String type, String jsondata) {
        IndexResponse response = client.prepareIndex(indexname, type)
                .setSource(jsondata)
                .execute()
                .actionGet();
        return response;
    }

    public List<SearchResult> searcher(QueryBuilder queryBuilder, String indexname, String type) {
        List<SearchResult> list = new ArrayList<SearchResult>();
        SearchResponse searchResponse = client.prepareSearch(indexname).setTypes(type)
                .setQuery(queryBuilder)
                .execute()
                .actionGet();
        SearchHits hits = searchResponse.getHits();
        System.out.println("查询到记录数=" + hits.getTotalHits());
        SearchHit[] searchHists = hits.getHits();
        if (searchHists.length > 0) {
            for (SearchHit hit : searchHists) {
                Long id = Long.valueOf((int) hit.getSource().get("id"));
                Date createAt = (Date) hit.getSource().get("createAt");
                String html = (String) hit.getSource().get("html");
                String brief = (String) hit.getSource().get("brief");
                String title = (String) hit.getSource().get("title");
                Date updateAt = (Date) hit.getSource().get("updateAt");
                String url = (String) hit.getSource().get("url");
                Long topic_id = Long.valueOf((int) hit.getSource().get("topic_id"));
                list.add(new SearchResult(id, createAt, updateAt, title, url, html, topic_id, brief));
            }
        }
        return list;
    }

    public List<SearchResult> search(String keyword) {
        QueryBuilder qb = new MultiMatchQueryBuilder(
                keyword,
                "title", "html"
        );
        return searcher(qb, esIndex, esType);

    }
}
