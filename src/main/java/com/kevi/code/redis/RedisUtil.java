package com.kevi.code.redis;

import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis工具类
 * 封装redisTemplate
 */
@Component
public class RedisUtil {

    private static final Logger logger = LoggerFactory.getLogger(RedisUtil.class);

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public RedisUtil(RedisTemplate<String, Object> redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    /**
     * 设定缓存失效时间
     * @param key 存储缓存的key
     * @param time 失效时间(毫秒)
     * @return
     */
    public boolean expire(@NonNull String key, long time){
        try {
            if (time > 0){
                redisTemplate.expire(key, time, TimeUnit.MILLISECONDS);
            }
            return true;
        } catch (Exception e){
            logger.error("【redis】设定缓存(key:"+key+")失效时间出错", e);
            return false;
        }
    }

    /**
     * 获取指定缓存过期时间
     * @param key 缓存key
     * @return  过期时间 long(毫秒)
     */
    public long getExpire(@NonNull String key){
        return redisTemplate.getExpire(key, TimeUnit.MILLISECONDS);
    }

    /**
     * 判断缓存key是否存在
     * (判断key代表的缓存是否存在)
     * @param key 缓存key
     * @return true 存在  false 不存在
     */
    public boolean hasKey(@NonNull String key){
        try {
            return redisTemplate.hasKey(key);
        }catch (Exception e){
            logger.error("【redis】判断缓存(key:"+key+")存在出错", e);
            return false;
        }
    }

    /**
     * 删除指定缓存
     * @param key 存储的key
     */
    public void deleteCache(String... key){
        if (key != null && key.length > 0){
            if (key.length == 1){
                redisTemplate.delete(key[0]);
            }else {
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }

    /**************************************** String ****************************************/

    /**
     * 依据key获取缓存
     * @param key 指定缓存的key
     * @return  缓存的值
     */
    public Object get(@NonNull String key){
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 存储缓存
     * @param key 缓存的key
     * @param value 缓存的value
     * @return
     */
    public boolean set(@NonNull String key, Object value){
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        }catch (Exception e){
            logger.error("【redis】【String】存储缓存(key:"+key+"，value:"+value+")出错", e);
            return false;
        }
    }

    /**
     * 存储缓存并设置过期时间
     * @param key 缓存的key
     * @param value 缓存的value
     * @param time 过期时间(毫秒)
     * @return
     */
    public boolean set(@NonNull String key, Object value, long time){
        try {
            if (time > 0){
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.MILLISECONDS);
            }else {
                redisTemplate.opsForValue().set(key, value);
            }
            return true;
        }catch (Exception e){
            logger.error("【redis】【String】存储缓存(key:"+key+"，value:"+value+", time:"+time+"(毫秒))出错", e);
            return false;
        }
    }

    /**
     * 递增
     * @param key 键
     * @param delta 要增加几(大于0)
     * @return
     */
    public long incr(@NonNull String key, long delta){
        if(delta<0){
            throw new RuntimeException("递增因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 递减
     * @param key 键
     * @param delta 要减少几(小于0)
     * @return
     */
    public long decr(@NonNull String key, long delta){
        if(delta<0){
            throw new RuntimeException("递减因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, -delta);
    }

    /**************************************** HashMap ****************************************/

    /**
     * 获取缓存中的HashMap中的值
     * @param key 缓存的key
     * @param item HashMap的key
     * @return
     */
    public Object hashGet(@NonNull String key, @NonNull Object item){
        return redisTemplate.opsForHash().get(key, item);
    }

    /**
     * 获取缓存中的HashMap
     * @param key 缓存的key
     * @return
     */
    public Map<Object, Object> hashMapGet(@NonNull String key){
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 存储数据至指定缓存中的HashMap
     * @param key 缓存的key
     * @param item  HashMap的key
     * @param value  想要存储的数据
     * @return
     */
    public boolean hashSet(@NonNull String key, @NonNull Object item, Object value){
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        }catch (Exception e){
            logger.error("【redis】【HashMap】存储缓存(key:"+key+"，item:"+item+"，value:"+value+")出错", e);
            return false;
        }
    }

    /**
     * 存储数据至指定缓存中的HashMap并重置缓存的过期时间
     * @param key 缓存的key
     * @param item  HashMap的key
     * @param value  想要存储的数据
     * @param time 缓存过期时间(毫秒)
     * @return
     */
    public boolean hashSet(@NonNull String key, @NonNull Object item, Object value, long time){
        try {
            redisTemplate.opsForHash().put(key, item, value);
            if (time > 0){
                expire(key, time);
            }
            return true;
        }catch (Exception e){
            logger.error("【redis】【HashMap】存储缓存(key:"+key+"，item:"+item+"，value:"+value+")出错", e);
            return false;
        }
    }

    /**
     * 存储HashMap数据类型的缓存
     * @param key 缓存的key
     * @param map 存储的HashMap
     * @return
     */
    public boolean hashMapSet(@NonNull String key, Map<String, Object> map){
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        }catch (Exception e){
//            String map_value = JSON.toJSONString(map);
            logger.error("【redis】【HashMap】存储缓存(key:"+key+"，map:"+map+")出错", e);
            return false;
        }
    }

    /**
     * 存储HashMap数据类型的缓存并设置过期时间
     * @param key 缓存的key
     * @param map 存储的HashMap
     * @param time 缓存过期时间
     * @return
     */
    public boolean hashMapSet(@NonNull String key, Map<String, Object> map, long time){
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if (time > 0){
                expire(key, time);
            }
            return true;
        }catch (Exception e){
//            String map_value = JSON.toJSONString(map);
            logger.error("【redis】【HashMap】存储缓存(key:"+key+"，map:"+map+")出错", e);
            return false;
        }
    }

    /**
     * 删除指定缓存中的HashMap中的指定key的值
     * @param key 缓存的key
     * @param item  HashMap的key
     * @return long 删除了几个数据
     */
    public long hashDelete(@NonNull String key, Object... item){
        return redisTemplate.opsForHash().delete(key, item);
    }

    /**
     * 判断指定缓存中的HashMap是否有指定的key
     * (判断item代表的数据是否存在)
     * @param key 缓存的key
     * @param item  HashMap的key
     * @return true 存在  false 不存在
     */
    public boolean hashHasKey(@NonNull String key, @NonNull String item){
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     * @param key 键
     * @param item 项
     * @param by 要增加几(大于0)
     * @return
     */
    public double hashIncr(@NonNull String key, String item, double by){
        return redisTemplate.opsForHash().increment(key, item, by);
    }

    /**
     * hash递减
     * @param key 键
     * @param item 项
     * @param by 要减少几(小于0)
     * @return
     */
    public double hashDecr(@NonNull String key, String item, double by){
        return redisTemplate.opsForHash().increment(key, item, -by);
    }

    /**************************************** Set ****************************************/

    /**
     * 获取指定缓存中的Set
     * @param key 缓存的key
     * @return
     */
    public Set<Object> setGet(@NonNull String key){
        try {
            return redisTemplate.opsForSet().members(key);
        }catch (Exception e){
            logger.error("【redis】【Set】获取缓存(key:"+key+")出错", e);
            return null;
        }
    }

    /**
     * 判断指定缓存中的Set是否存在指定成员
     * @param key   缓存的key
     * @param value 需要判断的Set成员
     * @return
     */
    public boolean setHasValue(@NonNull String key, @NonNull Object value){
        try {
            return redisTemplate.opsForSet().isMember(key, value);
        }catch (Exception e){
            logger.error("【redis】【Set】判断缓存(key:"+key+")set成员(value:"+value+")存在与否出错", e);
            return false;
        }
    }

    /**
     * 向指定缓存中添加Set成员(自动创建Set)
     * @param key   缓存的key
     * @param values    待添加的set成员
     * @return  成功添加的数量
     */
    public long setSet(@NonNull String key, Object... values){
        try {
            return redisTemplate.opsForSet().add(key, values);
        }catch (Exception e){
            logger.error("【redis】【Set】缓存(key:"+key+")添加成员(values:"+values+")出错", e);
            return 0;
        }
    }

    /**
     * 向指定缓存中添加Set成员(自动创建Set)并设置缓存过期时间
     * @param key   缓存的key
     * @param time 过期时间(毫秒)
     * @param values    待添加的set成员
     * @return  成功添加的数量
     */
    public long setSet(@NonNull String key, long time, Object... values){
        try {
            long count = redisTemplate.opsForSet().add(key, values);
            if (time > 0){
                expire(key, time);
            }
            return count;
        }catch (Exception e){
            logger.error("【redis】【Set】缓存(key:"+key+")添加成员(values:"+values+")并设置过期时间(time:"+time+")出错", e);
            return 0;
        }
    }

    /**
     * 获取指定缓存的Set大小
     * @param key   缓存的Key
     * @return  Set的大小
     */
    public long setGetSize(@NonNull String key){
        try {
            return redisTemplate.opsForSet().size(key);
        }catch (Exception e){
            logger.error("【redis】【Set】获取缓存(key:"+key+")Set大小出错", e);
            return 0;
        }
    }

    /**
     * 移除指定缓存的Set中的值
     * @param key   缓存的key
     * @param values    待移除的值
     * @return  移除成功的数量
     */
    public long setRemove(@NonNull String key, @NonNull Object... values){
        try {
            return redisTemplate.opsForSet().remove(key, values);
        }catch (Exception e){
            logger.error("【redis】【Set】移除缓存(key:"+key+")Set的值(values:"+values+")出错", e);
            return 0;
        }
    }

    /**************************************** List ****************************************/

    /**
     * 获取指定缓存中的部分List
     * @param key   缓存的key
     * @param start 开始位置
     * @param end   结束位置  (start：0 end：-1 代表获取所有值)
     * @return 截取的部分List
     */
    public List<Object> listGet(@NonNull String key, @NonNull long start, @NonNull long end){
        try {
            return redisTemplate.opsForList().range(key, start, end);
        }catch (Exception e){
            logger.error("【redis】【List】获取缓存(key:"+key+")中的部分List("+start+"-"+end+")", e);
            return null;
        }
    }

    /**
     * 获取指定缓存中List指定位置的值
     * @param key   缓存的key
     * @param index List位置/索引   具体指定规则如下所示
     *                              -2 (倒数第二个元素)
     *                              -1 (表尾) (最后一个元素) (倒数第一个元素)
     *                              0  (表头) (第一个元素)
     *                              1  (第二个元素)
     *                              2  (第三个元素)
     * @return List指定位置的值
     */
    public Object listGet(@NonNull String key, @NonNull long index){
        try {
            return redisTemplate.opsForList().index(key, index);
        }catch (Exception e){
            logger.error("【redis】【List】获取缓存(key:"+key+")List中指定位置(index:"+index+")的值", e);
            return null;
        }
    }

    /**
     * 获取指定缓存List的大小
     * @param key 缓存的key
     * @return  List的大小
     */
    public long listGetSize(@NonNull String key){
        try {
            return redisTemplate.opsForList().size(key);
        }catch (Exception e){
            logger.error("【redis】【List】获取缓存(key:"+key+")List大小", e);
            return 0;
        }
    }

    /**
     * 向指定缓存中的List添加元素
     * @param key   缓存的key
     * @param value 待添加的元素
     * @return  添加元素之后list的大小
     */
    public long listSet(@NonNull String key, Object value){
        try {
            return redisTemplate.opsForList().rightPush(key, value);
        }catch (Exception e){
            logger.error("【redis】【List】缓存(key:"+key+")List添加(rightPush)元素(value："+value+")", e);
            return -1;
        }
    }

    /**
     * 向指定缓存中的List添加元素并设置缓存过期时间
     * @param key   缓存的key
     * @param value 待添加的元素
     * @param time  过期时间(毫秒)
     * @return  添加元素之后list的大小
     */
    public long listSet(@NonNull String key, Object value, long time){
        try {
            long count = redisTemplate.opsForList().rightPush(key, value);
            if (time > 0){
                expire(key, time);
            }
            return count;
        }catch (Exception e){
            logger.error("【redis】【List】缓存(key:"+key+")List添加(rightPush)元素(value："+value+"),过期时间:"+time+"ms", e);
            return -1;
        }
    }

    /**
     * 向指定缓存中的List添加元素list
     * @param key   缓存的key
     * @param values 待添加的元素list
     * @return  添加元素之后list的大小
     */
    public long listSet(@NonNull String key, List<Object> values){
        try {
            return redisTemplate.opsForList().rightPushAll(key, values);
        }catch (Exception e){
            logger.error("【redis】【List】缓存(key:"+key+")List添加(rightPushAll)元素(values："+values+")", e);
            return -1;
        }
    }

    /**
     * 向指定缓存中的List添加元素list并设置缓存过期时间
     * @param key   缓存的key
     * @param values 待添加的元素list
     * @param time  过期时间(毫秒)
     * @return  添加元素之后list的大小
     */
    public long listSet(@NonNull String key, List<Object> values, long time){
        try {
            long count = redisTemplate.opsForList().rightPushAll(key, values);
            if (time > 0){
                expire(key, time);
            }
            return count;
        }catch (Exception e){
            logger.error("【redis】【List】缓存(key:"+key+")List添加(rightPushAll)元素(values："+values+"),过期时间:"+time+"ms", e);
            return -1;
        }
    }

    /**
     * 修改指定缓存中List指定位置的值
     * @param key   缓存的key
     * @param index List中的位置/索引
     * @param value 待替换的值
     * @return
     */
    public boolean listUpdate(@NonNull String key, long index, Object value){
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        }catch (Exception e){
            logger.error("【redis】【List】修改缓存(key:"+key+")List中指定位置(index:"+index+")值为"+value, e);
            return false;
        }
    }

    /**
     * 移除指定缓存中List的指定值
     * @param key   缓存的key
     * @param count 预计移除的数量 具体规则如下
     *                              -2 倒序移除2个
     *                              -1 倒序移除1个 (从右到左)
     *                              0 移除全部
     *                              1 正序移除1个 (从左到右)
     *                              2 正序移除2个
     * @param value 需要移除的值
     * @return  实际移除的数量
     */
    public long listRemove(@NonNull String key, long count, Object value){
        try {
            long remove = redisTemplate.opsForList().remove(key, count, value);
            return remove;
        }catch (Exception e){
            logger.error("【redis】【List】删除缓存(key:"+key+")List中指定值(value:"+value+"),预计删除"+count+"个(0代表全部）", e);
            return 0;
        }
    }

}
