package com.guozhaotong.controller;

import com.guozhaotong.mongoOperation.NewsFind;
import com.guozhaotong.redisOperation.RedisOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by tong in 18-5-9
 */
@RestController
public class NewsController {
    public static void main(String[] args) {
    }

    @RequestMapping("/hi")
    public String hi(){
        return "Hello";
    }

    @RequestMapping(value = "/find", produces = "text/plain")
    public String findNews(String word){
        String content = RedisOperation.findString(word);
        if(null == content) {
            content = NewsFind.findNews(word);
            RedisOperation.saveString(word, content);
            content = "no cache\n" + content;
        }
        return content;
    }
}
