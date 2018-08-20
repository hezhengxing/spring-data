package com.example.data.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;


/**
 * @Author:HeZhengXing
 * @Descripton:
 * @Date: Created in 18:01 2018/7/27
 * @Modify By:
 */
@Service
public class RedisTemplateUtil {

    /**
     * 日志记录
     */
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * redis基础操作类
     */
    private RedisTemplate redisTemplate;

    public RedisTemplateUtil(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 缓存String类型
     *
     * @param prefex 前缀
     * @param key    键
     * @param value  值
     * @return 是否成功
     */
    public boolean cacheStringValue(String prefex, String key, String value) {
        String keys = prefex + key;
        try {
            redisTemplate.boundValueOps(keys).set(value);
            return true;
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return false;
    }

    /**
     * 获取缓存String类型
     *
     * @param prefix 前缀
     * @param key    键
     * @return 是否成功
     */
    public String getStringValue(String prefix, String key) {
        try {
            String keys = prefix + key;
            ValueOperations<String, String> valueOps = redisTemplate.opsForValue();
            return valueOps.get(keys);
        } catch (Throwable t) {
            t.printStackTrace();
            return "";
        }
    }

    /**
     * 获取缓存Hash类型
     *
     * @param prefex 前缀
     * @param key    键
     * @return 是否成功
     */
    public Map<Object, Object> getHashMap(String prefex, String key) {
        try {
            String keys = prefex + key;
            HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
            return hash.entries(keys);
        } catch (Throwable t) {
            t.printStackTrace();
            return null;
        }
    }

    /**
     * 缓存list
     * @param k
     * @param v
     * @return
     */
    public boolean cacheList(String PREFIX,String k, List<String> v) {
        return cacheList(PREFIX,k, v, -1);
    }

    /**
     * 缓存list
     * @param k
     * @param v
     * @param time
     * @return
     */
    public boolean cacheList(String PREFIX,String k, List<String> v, long time) {
        String key = PREFIX + k;
        try {
            ListOperations listOps =  redisTemplate.opsForList();
            long l = listOps.rightPushAll(key, v);
            if (time > 0) redisTemplate.expire(key, time, TimeUnit.SECONDS);
            return true;
        } catch (Throwable t) {
            logger.error("缓存[" + key + "]失败, value[" + v + "]", t);
        }
        return false;
    }

    /**
     * 获取list缓存
     * @param k
     * @param start
     * @param end
     * @return
     */
    public List<String> getList(String PREFIX,String k, long start, long end) {
        try {
            ListOperations<String, String> listOps =  redisTemplate.opsForList();
            return listOps.range(PREFIX + k, start, end);
        } catch (Throwable t) {
            logger.error("获取list缓存失败key[" + PREFIX + k + ", error[" + t + "]");
        }
        return null;
    }

    /**
     * 获取list缓存
     * @param PREFIX
     * @param k
     * @return
     */
    public List<String> getList(String PREFIX,String k) {
        try {
            ListOperations<String, String> listOps =  redisTemplate.opsForList();
            return listOps.range(PREFIX + k, 0, getListSize(PREFIX,k));
        } catch (Throwable t) {
            logger.error("获取list缓存失败key[" + PREFIX + k + ", error[" + t + "]");
        }
        return null;
    }

    /**
     * 移除并返回列表 key 的头元素。
     * @param PREFIX
     * @param k
     * @return
     */
    public String getListLeftPop(String PREFIX,String k) {
        try {
            return redisTemplate.opsForList().leftPop(PREFIX+k).toString();
        } catch (Throwable t) {
            logger.error("获取list缓存失败key[" + PREFIX + k + ", error[" + t + "]");
        }
        return null;
    }



    /**
     * 获取总条数, 可用于分页
     * @param k
     * @return
     */
    public long getListSize(String PREFIX,String k) {
        try {
            ListOperations<String, String> listOps =  redisTemplate.opsForList();
            return listOps.size(PREFIX + k);
        } catch (Throwable t) {
            logger.error("获取list长度失败key[" + PREFIX + k + "], error[" + t + "]");
        }
        return 0;
    }

    /**
     * 获取总条数, 可用于分页
     * @param listOps
     * @param k
     * @return
     */
    public long getListSize(String PREFIX, ListOperations<String, String> listOps, String k) {
        try {
            return listOps.size(PREFIX + k);
        } catch (Throwable t) {
            logger.error("获取list长度失败key[" + PREFIX + k + "], error[" + t + "]");
        }
        return 0;
    }

    /**
     * 移除list缓存
     * @param k
     * @return
     */
    public boolean removeOneOfList(String PREFIX,String k) {
        String key = PREFIX + k;
        try {
            ListOperations<String, String> listOps =  redisTemplate.opsForList();
            listOps.rightPop(PREFIX + k);
            return true;
        } catch (Throwable t) {
            logger.error("移除list缓存失败key[" + PREFIX + k + ", error[" + t + "]");
        }
        return false;
    }

    /**
     * 移除缓存
     *
     * @param key
     * @return
     */
    public boolean remove(String prefix, String key) {
        try {
            redisTemplate.delete(prefix + key);
            return true;
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return false;
    }


    /**
     * 缓存set操作
     * @param k
     * @param v
     * @param time
     * @return
     */
    public boolean cacheSet(String PREFIX,String k, String v, long time) {
        String key = PREFIX + k;
        try {
            SetOperations<String, String> valueOps =  redisTemplate.opsForSet();
            valueOps.add(key, v);
            if (time > 0) redisTemplate.expire(key, time, TimeUnit.SECONDS);
            return true;
        } catch (Throwable t) {
            logger.error("缓存[" + key + "]失败, value[" + v + "]", t);
        }
        return false;
    }

    /**
     * 缓存set
     * @param k
     * @param v
     * @return
     */
    public boolean cacheSet(String PREFIX,String k, String v) {
        return cacheSet(PREFIX,k, v, -1);
    }

    /**
     * 缓存set
     * @param k
     * @param v
     * @param time
     * @return
     */
    public boolean cacheSet(String PREFIX, String k, Set<String> v, long time) {
        String key = PREFIX + k;
        try {
            SetOperations<String, String> setOps =  redisTemplate.opsForSet();
            setOps.add(key, v.toArray(new String[v.size()]));
            if (time > 0) redisTemplate.expire(key, time, TimeUnit.SECONDS);
            return true;
        } catch (Throwable t) {
            logger.error("缓存[" + key + "]失败, value[" + v + "]", t);
        }
        return false;
    }

    /**
     * 缓存set
     * @param k
     * @param v
     * @return
     */
    public boolean cacheSet(String PREFIX,String k, Set<String> v) {
        return cacheSet(PREFIX,k, v, -1);
    }

    /**
     * 获取缓存set数据
     * @param k
     * @return
     */
    public Set<String> getSet(String PREFIX,String k) {
        try {
            SetOperations<String, String> setOps = redisTemplate.opsForSet();
            return setOps.members(PREFIX + k);
        } catch (Throwable t) {
            logger.error("获取set缓存失败key[" + PREFIX + k + ", error[" + t + "]");
        }
        return null;
    }

    public boolean removeHash(String PREFIX,String k,String hashKey){
        String key = PREFIX + k;
        try {
            redisTemplate.opsForHash().delete(key, hashKey);
            return true;
        } catch (Throwable t) {
            logger.error("删除hash失败key[" + PREFIX + k + ", error[" + t + "]");
        }
        return false;
    }

    public boolean cacheHash(String PREFIX,String k,String hashKey,String value){
        return cacheHash(PREFIX,k,hashKey,value,-1);
    }

    public boolean cacheHash(String PREFIX,String k,String hashKey,String value,long time){
        String key = PREFIX + k;
        try {
            BoundHashOperations<String,String,String> boundHashOps = redisTemplate.boundHashOps(key);
            boundHashOps.put(hashKey,value);
            if (time > 0) redisTemplate.expire(key, time, TimeUnit.SECONDS);
            return true;
        } catch (Throwable t) {
            logger.error("保存hash缓存失败key[" + PREFIX + k + ", error[" + t + "]");
        }
        return false;
    }

    public boolean cacheHashAll(String PREFIX, String k, Map<String,String> hashKey, long time){
        String key = PREFIX + k;
        try {
            BoundHashOperations<String,String,String> boundHashOps = redisTemplate.boundHashOps(key);
            boundHashOps.putAll(hashKey);
            if (time > 0) redisTemplate.expire(key, time, TimeUnit.SECONDS);
            return true;
        } catch (Throwable t) {
            logger.error("保存hash缓存失败key[" + PREFIX + k + ", error[" + t + "]");
        }
        return false;
    }

    public boolean cacheHashAll(String PREFIX, String k, Map<String,String> hashKey){
        return cacheHashAll(PREFIX,k,hashKey,-1);
    }

    public String getHash(String PREFIX, String k, String hashKey){
        String key = PREFIX + k;
        try {
            BoundHashOperations<String,String,String> boundHashOps = redisTemplate.boundHashOps(key);
            return boundHashOps.get(hashKey);
        } catch (Throwable t) {
            logger.error("获取hash缓存失败key[" + PREFIX + k + ", error[" + t + "]");
        }
        return null;
    }

    /**
     * 缓存GEO多个member
     */
    public boolean cacheGeos(String key, Map<String,Point> map){
        try {
            BoundGeoOperations boundGeoOperations = redisTemplate.boundGeoOps(key);
            boundGeoOperations.geoAdd(map);
        } catch (Throwable t) {
            logger.error("保存多个Geo缓存失败key[" + key + ", error[" + t + "]");
        }
        return false;
    }

    /**
     * 缓存GEO member
     */
    public boolean cacheGeo(String key, Point point, String member){
        try {
            BoundGeoOperations boundGeoOperations = redisTemplate.boundGeoOps(key);
            boundGeoOperations.geoAdd(point,member);
        } catch (Throwable t) {
            logger.error("保存Geo缓存失败key[" + key + ", error[" + t + "]");
        }
        return false;
    }

    /**
     * 删除GEO member
     */
    public boolean removeGeo(String key, String member){
        try {
            BoundGeoOperations boundGeoOperations = redisTemplate.boundGeoOps(key);
            boundGeoOperations.geoRemove(member);
        } catch (Throwable t) {
            logger.error("删除Geo缓存失败key[" + key + ", error[" + t + "]");
        }
        return false;
    }

    /**
     * 获取距离某点point范围内distance的商家
     */
    public List geoRadius(String key, Point point, double distance){
        try {
            GeoOperations geoOperations = redisTemplate.opsForGeo();
            GeoResults geoResult = geoOperations.geoRadius(key, new Circle(point, new Distance(distance)));
            return geoResult.getContent();
        } catch (Throwable t) {
            logger.error("geoRadius缓存失败key[" + key + ", error[" + t + "]");
        }
        return null;
    }

    /**
     * 获取距离某点point范围内distance的商家，返回距离并排序
     */
    public List geoRadiusWithDistAndAsc(String key, Point point, double distance){
        try {
            GeoOperations geoOperations = redisTemplate.opsForGeo();
            GeoResults geoResult = geoOperations.geoRadius(key, new Circle(point, new Distance(distance)), RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs().includeDistance().sortAscending());
            return geoResult.getContent();
        } catch (Throwable t) {
            logger.error("geoRadiusWithDistAndAsc缓存失败key[" + key + ", error[" + t + "]");
        }
        return null;
    }
    /**
     * 校验对应的key是否存在
     *
     * @param key
     * @return
     */
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }
}

