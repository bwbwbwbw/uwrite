package edu.tongji.search;

import edu.tongji.article.Article;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import java.io.IOException;

/**
 * Created by pc-dll on 2015/6/25.
 */
public class JsonUtil {

    public static String obj2JsonData(Article article) {
        String jsonData = null;
        try {
            //使用XContentBuilder创建json数据
            XContentBuilder jsonBuild = XContentFactory.jsonBuilder();
            jsonBuild.startObject()
                    .field("id", article.getId())
                    .field("createAt", article.getCreateAt())
                    .field("html", article.getHtml())
                    .field("brief", article.getBrief())
                    .field("title", article.getTitle())
                    .field("updateAt", article.getUpdateAt())
                    .field("url", article.getUrl())
                    .field("topic_id", article.getTopic().getId())
                    .endObject();
            jsonData = jsonBuild.string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonData;
    }

}
