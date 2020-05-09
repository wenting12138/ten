package com.wen.article.service.impl;

import com.wen.article.ArticleMain9004;
import com.wen.article.repository.CommentRepository;
import com.wen.common.model.Comment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.util.Optional;

import static org.junit.Assert.*;

@SpringBootTest(classes = ArticleMain9004.class)
@RunWith(SpringRunner.class)
public class CommentServiceImplTest {

    @Resource
    private CommentRepository commentRepository;

    @Test
    public void test(){
        Optional<Comment> commentOptional = commentRepository.findById("123");
        if (commentOptional.isPresent()) {
            System.out.println(commentOptional.get());
        }
    }

}