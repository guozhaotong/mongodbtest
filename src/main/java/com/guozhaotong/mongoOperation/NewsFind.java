package com.guozhaotong.mongoOperation;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import com.guozhaotong.test.TestMongoDb;

import java.util.regex.Pattern;

/**
 * Created by tong in 18-5-9
 */
public class NewsFind {
    public static void main(String[] args) {
        findNews("中国");
    }

    public static String findNews(String word) {
        try {
            MongoCollection<Document> collection = TestMongoDb.getCollection("spider", "xjtuNews");
            //通过游标遍历检索出的文档集合
//            find查询条件：title like '%word%'。projection筛选：显示title,不显示_id(_id默认会显示)
            Pattern pattern = Pattern.compile(".*" + word + ".*", Pattern.CASE_INSENSITIVE);
            BasicDBObject query = new BasicDBObject();
            query.put("title", pattern);
            MongoCursor<Document> cursor = collection.find(query)
                    .projection(new Document("content", 1)
                            .append("_id", 0)).iterator();
            Thread.sleep(50);
            //查询所有数据
//            MongoCursor<Document> cursor= collection.find().iterator();
//            System.out.println(cursor);
            while (cursor.hasNext()) {
//                System.out.println(cursor.next().get("content").toString());
                return cursor.next().get("content").toString();

            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return "find nothing";
    }
}
