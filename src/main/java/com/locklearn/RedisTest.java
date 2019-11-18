package com.locklearn;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import redis.clients.jedis.Jedis;

public class RedisTest {

	public static void main(String[] args) {
            //连接Redis 服务
            Jedis jedis = new Jedis("192.168.101.65");
            System.out.println("连接成功");
            //查看服务是否运行

            System.out.println("服务正在运行: " + jedis.ping());
            //redis使用String
            jedis.set("name", "luolan");
            System.out.println(jedis.get("name"));
            //redis使用List（列表）
            String[] strings = {"List11", "List22"};
            jedis.lpush("redis-list", strings);
            List list = jedis.lrange("redis-list", 0, 10);
            for (int i = 0; i < list.size(); i++) {
                    System.out.println(list.get(i));
            }
            //查询命令窗口存储的list列表
            System.out.println("----------------------");
            List list1 = jedis.lrange("r", 0, 10);
            for (int i = 0; i < list1.size(); i++) {
                    System.out.println(list1.get(i));
            }
            System.out.println("*************************");
            //redis使用set（集合）
            String[] setsStrings = {"Set11", "Set22"};
            jedis.sadd("redis-set", setsStrings);
            Set set = jedis.smembers("redis-set");
            Iterator iterator = set.iterator();
            for (int i = 0; i < set.size(); i++) {
                    System.out.println(iterator.next());
            }
            //查询命令窗口存储的set列表
            System.out.println("----------------------");
            Set set1 = jedis.smembers("s");
            Iterator iterator1 = set1.iterator();
            for (int i = 0; i < set1.size(); i++) {
                    System.out.println(iterator1.next());
            }

    }
}
