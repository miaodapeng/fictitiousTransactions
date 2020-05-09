package com.vedeng.common.redis;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.exceptions.JedisException;
import redis.clients.util.Pool;

import javax.annotation.Resource;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <b>Description:</b><br> redis基础操作
 * @author cooper
 * @Note
 * <b>ProjectName:</b> erp.vedeng.com
 * <br><b>PackageName:</b> com.vedeng.common.redis
 * <br><b>ClassName:</b> RedisUtils
 * <br><b>Date:</b> 2019年5月27日 下午14:42:02
 */
public class RedisUtils {

    @Resource
    private Pool<Jedis> jedisPool;

    private Logger logger = LoggerFactory.getLogger(RedisUtils.class);

//    private Jedis jedis = null;
//    private boolean isBroken = false;//使用完成之后，放回连接池

    @Resource
    private RedisTemplate<String, ?> redisTemplate;

    /**
     * <b>Description:</b> 获取jedis对象
     * @return
     * @throws JedisException Jedis
     * @Note
     * <b>Author：</b> duke.li
     * <b>Date:</b> 2018年11月1日 上午9:42:22
     */
    private Jedis getJedis() throws JedisException {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
        }
        catch (JedisException e) {
            logger.error("getJedis error : jedisPool getResource.", e);
            if (jedis != null) {
                jedisPool.returnBrokenResource(jedis);
            }
            throw e;
        }
        return jedis;
    }

    /**
     * 释放jedis
     * @param jedis
     * @param isBroken
     */
    protected void release(Jedis jedis, boolean isBroken) {
        if (jedis != null) {
            if (isBroken) {
                //销毁对象
                jedisPool.returnBrokenResource(jedis);
            }else {
                //还回到连接池
                jedisPool.returnResource(jedis);
            }
        }
    }

    /**
     * 清空缓存数据
     */
    public void flushAll(){
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = this.getJedis();
            jedis.flushAll();
        }catch (Exception e) {
            isBroken = true;//若出现异常，则销毁
        }finally {
            release(jedis, isBroken);//使用完成之后，放回连接池
        }
    }

    /**
     * 新增or修改 key-value均为String
     * @param key
     * @param value
     */
    public void set(String key, String value) {
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = this.getJedis();
            jedis.set(key, value);
        }catch (Exception e) {
            isBroken = true;//若出现异常，则销毁
        }finally {
            release(jedis, isBroken);//使用完成之后，放回连接池
        }
    }
    /**
     * <b>Description:</b> 新增or修改 key-value均为String 设置有效时间
     * @param key
     * @param value
     * @param cacheSeconds void
     * @Note
     * <b>Author：</b> duke.li
     * <b>Date:</b> 2018年11月1日 上午9:42:35
     */
    public void set(String key, String value, int cacheSeconds) {
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = this.getJedis();
            jedis.set(key, value);
            if(cacheSeconds != 0){
                jedis.expire(key, cacheSeconds);
            }
        }catch (Exception e) {
            isBroken = true;//若出现异常，则销毁
        }finally {
            release(jedis, isBroken);//使用完成之后，放回连接池
        }
    }

    /**
     * 存储自定义对象
     * @param key
     * @param object -- 自定义对象
     */
    public void set(byte[] key, Object object) {
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = this.getJedis();
            jedis.set(key, SerializeUtil.serialize(object));
        }catch (Exception e) {
            isBroken = true;//若出现异常，则销毁
        }finally {
            release(jedis, isBroken);//使用完成之后，放回连接池
        }
    }

    /**
     * 获取自定义对象
     * @param key
     * @param object -- 自定义对象
     */
    public Object get(byte[] key,Object object) {
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = this.getJedis();
            byte[] bs = jedis.get(key);
            if(object!=null)
                object = (Object) SerializeUtil.unserialize(bs);
            else
                object=null;
        }catch (Exception e) {
            isBroken = true;//若出现异常，则销毁
        }finally {
            release(jedis, isBroken);//使用完成之后，放回连接池
        }
        return object;
    }


    /**
     * 新增or修改 key为int--value为String
     * @param key
     * @param value
     */
    public void set(Integer key, String value) {
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = this.getJedis();
            jedis.set(String.valueOf(key), value);
        }
        catch (Exception e) {
            isBroken = true;
        }finally {
            release(jedis, isBroken);
        }
    }
    public void set(Integer key, String value, int cacheSeconds) {
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = this.getJedis();
            jedis.set(String.valueOf(key), value);
            if(cacheSeconds != 0){
                jedis.expire(String.valueOf(key), cacheSeconds);
            }
        }catch (Exception e) {
            isBroken = true;//若出现异常，则销毁
        }finally {
            release(jedis, isBroken);//使用完成之后，放回连接池
        }
    }
    /**
     * 获取-参数类型String
     * @param key
     * @return
     */
    public String get(String key) {
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = this.getJedis();
            return jedis.get(key);
        }catch (Exception e) {
            isBroken = true;
        }finally {
            release(jedis, isBroken);
        }
        return null;
    }

    /**
     * 获取-参数类型Object
     * @param key
     * @return
     */
    public String getObj(Object key) {
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = this.getJedis();
            return jedis.get(key.toString());
        }catch (Exception e) {
            isBroken = true;
        }finally {
            release(jedis, isBroken);
        }
        return "error";
    }

    /**
     * 获取-参数类型int
     * @param key
     * @return
     */
    public String get(Integer key) {
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = this.getJedis();
            return jedis.get(String.valueOf(key));
        }catch (Exception e) {
            isBroken = true;
        }finally {
            release(jedis, isBroken);
        }
        return null;
    }

    public Set<String> getKeys(String key) {
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = this.getJedis();
            return jedis.keys(key);
        }catch (Exception e) {
            isBroken = true;
        }finally {
            release(jedis, isBroken);
        }
        return null;
    }

    /**
     * 删除-参数类型String
     * @param key
     */
    public void del(String key) {
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = this.getJedis();
            jedis.del(key);
        }catch (Exception e) {
            isBroken = true;
        }finally {
            release(jedis, isBroken);
        }
    }

    /**
     * 删除-参数类型int
     * @param key
     */
    public void del(int key) {
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = this.getJedis();
            jedis.del(String.valueOf(key));
        }catch (Exception e) {
            isBroken = true;
        }finally {
            release(jedis, isBroken);
        }
    }
    /**
     * <b>Description:</b> 模糊删除
     * @param key void
     * @Note
     * <b>Author：</b> duke.li
     * <b>Date:</b> 2018年11月5日 下午2:28:05
     */
    public void delete(String key) {
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = this.getJedis();
            Set<String> keys = jedis.keys(key);
            if (CollectionUtils.isNotEmpty(keys)) {
                redisTemplate.delete(keys);
            }
        }catch (Exception e) {
            isBroken = true;
        }finally {
            release(jedis, isBroken);
        }
    }

    /**
     * 是否存在键值key为str的记录 存在返回true
     * @param str
     * @return
     */
    public Boolean exists(String str) {
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = this.getJedis();
            return jedis.exists(str);
        }catch (Exception e) {
            isBroken = true;
        }finally {
            release(jedis, isBroken);
        }
        return false;
    }
    public Boolean exists(Integer str) {
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = this.getJedis();
            return jedis.exists(String.valueOf(str));
        }catch (Exception e) {
            isBroken = true;
        }finally {
            release(jedis, isBroken);
        }
        return false;
    }

    /**
     * 设置key的生存时间
     * 如果对key使用set或del命令，那么也会移除expire time
     * @param key
     * @param seconds
     */
    public void expire(String key, int seconds) {
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = this.getJedis();
            jedis.expire(key, seconds);
        }catch (Exception e) {
            isBroken = true;
        }finally {
            release(jedis, isBroken);
        }
    }

    public void expireV(String key, int seconds) {
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = this.getJedis();
            jedis.expire(key, seconds);
        }catch (Exception e) {
            isBroken = true;
        }finally {
            release(jedis, isBroken);
        }
    }

    /**
     * 以Unix时间戳格式设置键的到期时间
     * @param key
     * @param unixTime
     */
    public void expireAt(String key, long unixTime) {
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = this.getJedis();
            jedis.expireAt(key, unixTime);
        }catch (Exception e) {
            isBroken = true;
        }finally {
            release(jedis, isBroken);
        }
    }

    /**
     * 用于在存储的关键值的散列设置字段。如果键不存在，新的key由哈希创建。如果字段已经存在于哈希值那么将被覆盖
     * http://www.yiibai.com/redis/hashes_hset.html
     * @param key
     * @param field
     * @param value
     */
    public void hset(String key, Object field, String value) {
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = this.getJedis();
            jedis.hset(key, field.toString(), value);
        }catch (Exception e) {
            isBroken = true;
            logger.error("hset " + key + " " + field + " " + value, e);
        }finally {
            release(jedis, isBroken);
        }
    }

    /**
     * http://www.yiibai.com/redis/hashes_hset.html
     * @param key
     * @param field
     * @param value
     */
    public void hset(String key, String field, Integer value) {
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = this.getJedis();
            jedis.hset(key, field, String.valueOf(value));
        }catch (Exception e) {
            isBroken = true;
        }finally {
            release(jedis, isBroken);
        }
    }

    /**
     * http://www.yiibai.com/redis/hashes_hset.html
     * @param key
     * @param field
     * @param value
     */
    public void hset(Integer key, String field, Integer value) {
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = this.getJedis();
            jedis.hset(String.valueOf(key), field, String.valueOf(value));
        }catch (Exception e) {
            isBroken = true;
        }finally {
            release(jedis, isBroken);
        }
    }

    /**
     * http://www.yiibai.com/redis/hashes_hset.html
     * @param key
     * @param field
     * @param value
     */
    public void hset(Integer key, String field, String value) {
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = this.getJedis();
            jedis.hset(String.valueOf(key), field, value);
        }catch (Exception e) {
            isBroken = true;
        }finally {
            release(jedis, isBroken);
        }
    }

    /**
     * 用于获取与字段中存储的键哈希相关联的值。
     * http://www.yiibai.com/redis/hashes_hget.html
     * @param key
     * @param field
     * @return
     */
    public String hget(String key, Object field) {
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = this.getJedis();
            return jedis.hget(key, field.toString());
        }catch (Exception e) {
            isBroken = true;
        }finally {
            release(jedis, isBroken);
        }
        return null;
    }

    public String hgetV(String key, String field) {
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = this.getJedis();
            return jedis.hget(key, field);
        }catch (Exception e) {
            isBroken = true;
        }finally {
            release(jedis, isBroken);
        }
        return null;
    }

    public String hget(Integer key, String field) {
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = this.getJedis();
            return jedis.hget(String.valueOf(key), field);
        }catch (Exception e) {
            isBroken = true;
        }finally {
            release(jedis, isBroken);
        }
        return null;
    }

    /**
     * http://www.yiibai.com/redis/hashes_hdel.html
     * @param key
     * @param field
     */
    public void hdel(String key, String field) {
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = this.getJedis();
            jedis.hdel(key, field);
        }catch (Exception e) {
            isBroken = true;
        }finally {
            release(jedis, isBroken);
        }
    }

    //http://www.yiibai.com/redis/hashes_hgetall.html
    public Map<String, String> hgetAll(String key) {
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = this.getJedis();
            return jedis.hgetAll(key);
        }catch (Exception e) {
            isBroken = true;
        }finally {
            release(jedis, isBroken);
        }
        return null;
    }

    /**
     * 向一个集合中添加一个元素
     * 可一次添加多个  jedis.sadd("fruit", "pear", "watermelon");
     * @param key
     * @param member
     */
    public void sadd(String key, Integer member) {
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = this.getJedis();
            jedis.sadd(key, String.valueOf(member));
        }catch (Exception e) {
            isBroken = true;
        }finally {
            release(jedis, isBroken);
        }
    }

    public void srem(String key, Integer member) {
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = this.getJedis();
            jedis.srem(key, String.valueOf(member));
        }
        catch (Exception e) {
            isBroken = true;
        }
        finally {
            release(jedis, isBroken);
        }
    }

    public void srem(String key, String member) {
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = this.getJedis();
            jedis.srem(key, member);
        }
        catch (Exception e) {
            isBroken = true;
        }
        finally {
            release(jedis, isBroken);
        }
    }

    public void zadd(String key, Double score, String member) {
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = this.getJedis();
            jedis.zadd(key, score, member);
        }
        catch (Exception e) {
            isBroken = true;
        }
        finally {
            release(jedis, isBroken);
        }
    }

    public void zadd(String key, Map<String, Double> scoreMembers) {
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = this.getJedis();
            jedis.zadd(key, scoreMembers);
        }
        catch (Exception e) {
            isBroken = true;
        }
        finally {
            release(jedis, isBroken);
        }
    }

    public void zrem(String key, String member) {
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = this.getJedis();
            jedis.zrem(key, member);
        }
        catch (Exception e) {
            isBroken = true;
        }
        finally {
            release(jedis, isBroken);
        }
    }

    public Set<String> zrevrange(String key, long start, long end) {
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = this.getJedis();
            return jedis.zrevrange(key, start, end);
        }
        catch (Exception e) {
            isBroken = true;
        }
        finally {
            release(jedis, isBroken);
        }
        return null;
    }

    public Set<String> zrevrangebyscore(String key, String max, String min, int offset, int count) {
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = this.getJedis();
            return jedis.zrevrangeByScore(key, max, min, offset, count);
        }
        catch (Exception e) {
            isBroken = true;
        }
        finally {
            release(jedis, isBroken);
        }
        return null;
    }

    /**
     * 判断Set是否包含制定元素member
     * http://blog.itpub.net/29754888/viewspace-1261575/
     * @param key
     * @param member
     * @return
     */
    public boolean sismember(String key, String member) {
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = this.getJedis();
            return jedis.sismember(key, member);
        }catch (Exception e) {
            isBroken = true;
        }finally {
            release(jedis, isBroken);
        }
        return false;
    }

    public boolean sismember(String key, Object member) {
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = this.getJedis();
            return jedis.sismember(key, member.toString());
        }
        catch (Exception e) {
            isBroken = true;
        }
        finally {
            release(jedis, isBroken);
        }
        return false;
    }

    /**
     * 修改key，将原oldkey修改为newkey
     * @param oldkey
     * @param newkey
     */
    public void rename(String oldkey, String newkey) {
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = this.getJedis();
            jedis.rename(oldkey, newkey);
        }
        catch (Exception e) {
            isBroken = true;
        }
        finally {
            release(jedis, isBroken);
        }
    }


    public long incrByNumber(String key, int number) {
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = this.getJedis();
            return jedis.incrBy(key, number);
        }
        catch (Exception e) {
            isBroken = true;
        }
        finally {
            release(jedis, isBroken);
        }
        return 0;
    }

    public Set<String> smembers(String key) {
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = this.getJedis();
            return jedis.smembers(key);
        }
        catch (Exception e) {
            isBroken = true;
            return null;
        }
        finally {
            release(jedis, isBroken);
        }
    }

    public String srandmember(String key) {
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = this.getJedis();
            return jedis.srandmember(key);
        }
        catch (Exception e) {
            isBroken = true;
            return null;
        }
        finally {
            release(jedis, isBroken);
        }
    }

    public void sadd(String key, String... members) {
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = this.getJedis();
            jedis.sadd(key, members);
        }
        catch (Exception e) {
            isBroken = true;
        }
        finally {
            release(jedis, isBroken);
        }
    }

    public long scard(String key) {
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = this.getJedis();
            return jedis.scard(key);
        }
        catch (Exception e) {
            isBroken = true;
            return -1;
        }
        finally {
            release(jedis, isBroken);
        }
    }

    public void setex(String key, String value, int seconds) {
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = this.getJedis();
            jedis.setex(key, seconds, value);
        }
        catch (Exception e) {
            isBroken = true;
        }
        finally {
            release(jedis, isBroken);
        }
    }

    public long zcard(String key) {
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = this.getJedis();
            return jedis.zcard(key);
        }
        catch (Exception e) {
            isBroken = true;
            return -1;
        }
        finally {
            release(jedis, isBroken);
        }
    }

    public Long zrank(String key, String member) {
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = this.getJedis();
            return jedis.zrank(key, member);
        }
        catch (Exception e) {
            isBroken = true;
            return -1L;
        }
        finally {
            release(jedis, isBroken);
        }
    }

    public double zincrby(String key, double score, String member) {
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = this.getJedis();
            return jedis.zincrby(key, score, member);
        }
        catch (Exception e) {
            isBroken = true;
            return -1d;
        }
        finally {
            release(jedis, isBroken);
        }
    }

    public Double zscore(String key, String member) {
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = this.getJedis();
            return jedis.zscore(key, member);
        }
        catch (Exception e) {
            isBroken = true;
            return -1d;
        }
        finally {
            release(jedis, isBroken);
        }
    }

    public Long zcount(String key, double min, double max) {
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = this.getJedis();
            return jedis.zcount(key, min, max);
        }
        catch (Exception e) {
            isBroken = true;
            return 0l;
        }
        finally {
            release(jedis, isBroken);
        }
    }

    public long zrevrank(String key, String member) {
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = this.getJedis();
            return jedis.zrevrank(key, member);
        }
        catch (Exception e) {
            isBroken = true;
            return -1l;
        }
        finally {
            release(jedis, isBroken);
        }
    }

    public boolean hset(String key, Map<String, String> hash) {
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = this.getJedis();
            jedis.hmset(key, hash);
            return true;
        }
        catch (Exception e) {
            isBroken = true;
            return false;
        }
        finally {
            release(jedis, isBroken);
        }
    }

    public List<String> hmget(String key, String... fields) {
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = this.getJedis();
            return jedis.hmget(key, fields);
        }
        catch (Exception e) {
            isBroken = true;
            return null;
        }
        finally {
            release(jedis, isBroken);
        }
    }

    public void subscribe(JedisPubSub jedisPubSub, String... channels) {
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = this.getJedis();
            jedis.subscribe(jedisPubSub, channels);
        }
        catch (Exception e) {
            isBroken = true;
        }
        finally {
            release(jedis, isBroken);
        }
    }

    /**
     * 方法描述:新增一个Redis队列对象,并从左侧注入值 list的key值,队列的值
     * @param
     * @return 返回是否成功,成功:true 失败:false
     */
    public boolean lpushRedisStrValue(String key,String...value){
        Jedis jedis = null;
        boolean isBroken = false;
        try{
            jedis = this.getJedis();
            //资源不为空则执行注入操作 否则返回注入失败
            if(jedis != null){
                jedis.lpush(key, value);
            }else{
                isBroken = false;
            }
        }catch (Exception e) {
            isBroken = true;
        }
        finally {
            release(jedis, isBroken);
        }
        return isBroken;
    }

    /**
     * 方法描述:新增一个Redis队列对象,并从右侧注入值 list的key值,list对象
     * @param
     * @return 返回是否成功,成功:true 失败:false
     */
    public boolean rpushRedisStrValue(String key,String...value){
        Jedis jedis = null;
        boolean isBroken = false;
        try{
            //资源不为空则执行注入操作 否则返回注入失败
            jedis = this.getJedis();
            if(jedis != null){
                jedis.rpush(key, value);
            }else{
                isBroken = false;
            }
        }catch (Exception e) {
            isBroken = true;
        }finally {
            release(jedis, isBroken);
        }
        return isBroken;
    }

    /**
     * 获取缓存中所有键值
     * @param key
     * @return
     */
    public Set<String> hkeys(String key) {
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = this.getJedis();
            return jedis.hkeys(key);
        }
        catch (Exception e) {
            isBroken = true;
        }
        finally {
            release(jedis, isBroken);
        }
        return null;
    }

    public long hincrBy(String key, String field, long value) {
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = this.getJedis();
            return jedis.hincrBy(key, field, value);
        }
        catch (Exception e) {
            isBroken = true;
        }
        finally {
            release(jedis, isBroken);
        }
        return 0l;
    }

    public String lpop(String key) {
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = this.getJedis();
            return jedis.lpop(key);
        }
        catch (Exception e) {
            isBroken = true;
        }
        finally {
            release(jedis, isBroken);
        }
        return null;
    }

    public void publish(String channel, String message) {
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = this.getJedis();
            jedis.publish(channel, message);
        }
        catch (Exception e) {
            isBroken = true;
        }
        finally {
            release(jedis, isBroken);
        }
    }

    public boolean hsetnx(String key, String field, String value) {
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = this.getJedis();
            return jedis.hsetnx(key, field, value) == 1;
        }
        catch (Exception e) {
            isBroken = true;
        }
        finally {
            release(jedis, isBroken);
        }
        return false;
    }

    public boolean setnx(String key, String value) {
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = this.getJedis();
            return jedis.setnx(key, value) == 1;
        }
        catch (Exception e) {
            isBroken = true;
        }
        finally {
            release(jedis, isBroken);
        }
        return false;
    }

    public boolean hexists(String key, String field) {
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = this.getJedis();
            return jedis.hexists(key, field);
        }
        catch (Exception e) {
            isBroken = true;
        }
        finally {
            release(jedis, isBroken);
        }
        return false;
    }

    public int hgetIntValue(String key, String field) {
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = this.getJedis();
            return Integer.parseInt(jedis.hget(key, field));
        }catch (Exception e) {
            isBroken = true;
        }finally {
            release(jedis, isBroken);
        }
        return 0;
    }

    /**
     * <b>Description:只修改Value值不更改生存时间</b><br>
     *
     * @param :[key, value]
     * @return :void
     * @Note <b>Author:</b> Michael <br>
     *       <b>Date:</b> 2018/11/24 3:11 PM
     */
    public void setrange(String key, String value) {
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = this.getJedis();
            //老得value值
            String oldValue = jedis.get(String.valueOf(key));
            jedis.setrange(String.valueOf(key),0,value);
        }
        catch (Exception e) {
            isBroken = true;
        }finally {
            release(jedis, isBroken);
        }
    }

    
    /** @description: 返回key的剩余时间（秒）.
     * @notes: .
     * @author: Tomcat.Hui.
     * @date: 2019/8/28 10:56.
     * @param key
     * @return: long.
     * @throws: .
     */
    public long ttl(String key) {
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = this.getJedis();
            return jedis.ttl(key);
        }
        catch (Exception e) {
            isBroken = true;
        }
        finally {
            release(jedis, isBroken);
        }
        return -2;
    }
    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";
 
    /**
     * 尝试获取分布式锁
     * @param jedis Redis客户端
     * @param lockKey 锁
     * @param requestId 请求标识
     * @param expireTime 超期时间
     * @return 是否获取成功
     * @author: strange.dai
     * @date 2019年9月24日
     */
    public boolean tryGetDistributedLock(String lockKey, String requestId, int expireTime) {
    	Jedis jedis = null;
    	boolean isBroken = false;
    	try {
    		jedis = this.getJedis();
    		String result = jedis.set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);

    		if (LOCK_SUCCESS.equals(result)) {
    			return true;
    		}
    		return false;
    	} catch (Exception e) {
    		isBroken = true;
    	}
    	finally {
    		release(jedis, isBroken);
    	}
    	return false;
    }

    private static final Long RELEASE_SUCCESS = 1L;
 
    /**
     * 释放分布式锁
     * @param jedis Redis客户端
     * @param lockKey 锁
     * @param requestId 请求标识
     * @return 是否释放成功
     * @author: strange.dai
     * @date 2019年9月24日
     */
    public boolean releaseDistributedLock(String lockKey, String requestId) {
    	Jedis jedis = null;
    	boolean isBroken = false;
    	try {
    		jedis = this.getJedis();
    		String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
    		Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));
    		
    		if (RELEASE_SUCCESS.equals(result)) {
    			return true;
    		}
    		return false;
		} catch (Exception e) {
			isBroken=true;
		}finally {
			release(jedis, isBroken);
		}
    	return false;
    }



}
