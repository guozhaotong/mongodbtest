package com.guozhaotong.redisOperation;

import redis.clients.jedis.Jedis;

/**
 * Created by tong in 18-5-9
 */
public class RedisOperation {
    public static void main(String[] args) {
    }

    public static void saveString(String word, String content){
        //连接本地的 Redis 服务
        Jedis jedis = new Jedis("localhost");
        //设置 redis 字符串数据
        jedis.set(word, content);
        jedis.expire(word, 30);
//        System.out.println("redis 存储的字符串为: "+ jedis.get("runoobkey"));
    }

    public static String findString(String word){
        Jedis jedis = new Jedis("localhost");
        return jedis.get(word);
    }
}
