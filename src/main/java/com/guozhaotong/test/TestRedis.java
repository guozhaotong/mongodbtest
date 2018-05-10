package com.guozhaotong.test;

import redis.clients.jedis.Jedis;

/**
 * Created by tong in 18-5-9
 */
public class TestRedis {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("localhost");
        System.out.println("连接成功");
        //设置 redis 字符串数据
        jedis.set("runoobkey", "www.runoob.com");
        jedis.expire("runoobkey", 2);

        // 获取存储的数据并输出
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("redis 存储的字符串为: "+ jedis.get("runoobkey"));
    }
}
