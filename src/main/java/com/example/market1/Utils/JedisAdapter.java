package com.example.market1.Utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Component
public class JedisAdapter implements InitializingBean {

    private JedisPool pool;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.pool = new JedisPool("localhost",6379);
    }

    public Jedis getJedis(){
        return pool.getResource();
    }

    public String get(String key){
        Jedis jedis = null;
        try{
            jedis = pool.getResource();
            return jedis.get(key);
        }catch (Exception e){
            return null;
        }finally {
            jedis.close();
        }
    }

    public String set(String key, String value){
        Jedis jedis = null;
        try{
            jedis = pool.getResource();
            return jedis.set(key, value);
        }catch (Exception e){
            return null;
        }finally {
            jedis.close();
        }
    }

    public Boolean sismember(String key, String value){
        Jedis jedis = null;
        try{
            jedis = pool.getResource();
            return jedis.sismember(key, value);
        }catch (Exception e){
            return false;
        }finally {
            jedis.close();
        }
    }

    public Long sadd(String key, String value){
        Jedis jedis = null;
        try{
            jedis = pool.getResource();
            return jedis.sadd(key, value);
        }catch (Exception e){
            return (long)0;
        }finally {
            jedis.close();
        }
    }

    public Long srem(String key, String value){
        Jedis jedis = null;
        try{
            jedis = pool.getResource();
            return jedis.srem(key, value);
        }catch (Exception e){
            return (long)0;
        }finally {
            jedis.close();
        }
    }

    public Long scard(String key){
        Jedis jedis = null;
        try{
            jedis = pool.getResource();
            return jedis.scard(key);
        }catch (Exception e){
            return (long)0;
        }finally {
            jedis.close();
        }
    }

    public static void print(int index, Object object){
        System.out.println(String.format("%d,%s",index, object.toString()));
    }

    /*
    public static void main(String [] args){
        Jedis jedis = new Jedis();
        jedis.flushAll();

        jedis.set("1","2");
        print(1, jedis.get("1"));
    }
     */
}
