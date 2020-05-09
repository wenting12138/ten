package com.wem.test;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.HashMap;
import java.util.Map;

public class MongodbTest {

    // 创建客户端
    MongoClient mongoClient;
    // 获取集合
    MongoCollection<Document> comment;

    @Before
    public void init(){
        // 创建客户端
        mongoClient = new MongoClient("192.168.73.128", 27017);
        // 选择数据库
        MongoDatabase database = mongoClient.getDatabase("commentdb");
        // 获取集合
        comment = database.getCollection("comment");
    }
    @After
    public void close(){
        // 关闭客户端, 释放资源
        mongoClient.close();
    }

    @Test
    public void test(){
        // 查询数据
        FindIterable<Document> documents = comment.find();
        parseResult(documents);
    }

    // 条件查询
    @Test
    public void test2(){
        // 封装查询条件
        BasicDBObject bson = new BasicDBObject("_id", "123");
        // 执行查询
        FindIterable<Document> documents = comment.find(bson);
        // 解析数据
        parseResult(documents);
    }

    // 写入
    @Test
    public void insert(){
        Map<String, Object> map = new HashMap<>();
        map.put("_id", "456");
        map.put("name", "哈哈哈");
        Document document = new Document(map);
        comment.insertOne(document);
        System.out.println("新增成功");
    }

    // 修改
    @Test
    public void update(){
        BasicDBObject filter = new BasicDBObject("_id", "456");
        BasicDBObject update = new BasicDBObject("$set", new Document("name", "王五"));

        comment.updateOne(filter, update);
        System.out.println("修改成功");
    }

    // 删除:
    @Test
    public void del(){
        BasicDBObject bson = new BasicDBObject("name", "张三");
        comment.deleteOne(bson);
        System.out.println("删除成功");
    }



    private void parseResult(FindIterable<Document> documents) {
        // 解析数据
        for (Document document : documents) {
            System.out.println("id: " + document.get("_id"));
            System.out.println("name: " + document.get("name"));
        }
    }
}
