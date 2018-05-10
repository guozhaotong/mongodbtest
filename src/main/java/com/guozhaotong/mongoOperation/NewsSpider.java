package com.guozhaotong.mongoOperation;

import com.mongodb.client.MongoCollection;
import com.guozhaotong.model.News;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import com.guozhaotong.utils.JsonUtils;
import com.guozhaotong.utils.MongoManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tong in 18-5-9
 */
public class NewsSpider {
    public static void main(String[] args) {
        System.out.println("first page...");
        spider("http://newsxq.xjtu.edu.cn/zyxw.htm");
        System.out.println("second page...");
        spider("http://newsxq.xjtu.edu.cn/zyxw/400.htm");
        System.out.println("third page...");
        spider("http://newsxq.xjtu.edu.cn/zyxw/399.htm");
    }

    public static void spider(String url) {
        List<News> list = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(url).timeout(3000).get();
            doc.select(".l_li a").forEach(d -> list.add(getNews(d.attr("abs:href"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        MongoManager mongoManager = new MongoManager("127.0.0.1", 27017, "spider");
        MongoCollection<org.bson.Document> collection = mongoManager.getMongoClient().getDatabase("spider").getCollection("xjtuNews");
        list.forEach(n -> collection.insertOne(org.bson.Document.parse(JsonUtils.toJson(n))));
    }

    public static News getNews(String url) {
        News news = new News();
        try {
            Document doc = Jsoup.connect(url).timeout(3000).get();
            news.setTitle(doc.select("body > div.pw > div.i_left > form > div > div.d_title").text());
            news.setContent(doc.html());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return news;
    }
}
