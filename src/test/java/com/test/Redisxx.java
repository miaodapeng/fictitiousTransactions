package com.test;

import redis.clients.jedis.Jedis;

public class Redisxx {
    public static void main(String[] args) {

            String key="shiro_redis_session:3535a8ed-fabb-4ec6-8762-f1ff6f23e834";
            Jedis jedis=new Jedis("192.168.1.58",6379	);
            System.out.println(jedis.info());

    }
}
