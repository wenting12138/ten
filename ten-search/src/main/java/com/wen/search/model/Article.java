package com.wen.search.model;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import javax.persistence.Id;
import java.io.Serializable;

@Document(indexName = "ten_article", type = "article", shards = 1, replicas = 0)
@Data
public class Article implements Serializable {

    @Id
    private String id;

    @Field(index = true, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String title;

    @Field(index = true, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String content;

    private String state; // 状态

}
