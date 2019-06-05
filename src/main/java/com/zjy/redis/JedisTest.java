package com.zjy.redis;

import redis.clients.jedis.*;

import java.util.HashSet;
import java.util.Set;

/**
 * @author zjy
 * @des
 * @date 2019/6/4
 */
public class JedisTest {
    public static void main(String[] args) {

    }

    public static JedisPool singlePool = null;

    public void JedisPoolTest(){

        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(500);
        config.setMaxIdle(100);
        config.setMinIdle(10);
        config.setMaxWaitMillis(30);
        config.setMinEvictableIdleTimeMillis(300000);
        config.setSoftMinEvictableIdleTimeMillis(-1);
        config.setNumTestsPerEvictionRun(3);
        config.setTestOnBorrow(false);
        config.setTestOnReturn(false);
        config.setTestWhileIdle(false);
        config.setTimeBetweenEvictionRunsMillis(60000);//一分钟
        config.setBlockWhenExhausted(true);
        JedisPool pool = new JedisPool(config, "host", 6379, 30, 30,
                "pwd", 0, "clickname");
        singlePool = pool;
        Jedis jedis = pool.getResource();
        jedis.set("testkey", "testvalue");
        jedis.hset("test1key", "test1field", "test1value");
        jedis.close();

    }


    public void JedisClusterTest(){

        Set<HostAndPort> hostAndPortSet=new HashSet<>();
        hostAndPortSet.add(new HostAndPort("host1",7001));
        hostAndPortSet.add(new HostAndPort("host2",7002));
        hostAndPortSet.add(new HostAndPort("host3",7003));

        JedisPoolConfig config = new JedisPoolConfig();
        JedisCluster jedisCluster=new JedisCluster(hostAndPortSet,config);
        jedisCluster.set("key","value1");
        jedisCluster.get("key");

    }



    private static Jedis getJedis() {
        return singlePool.getResource();
    }

    public static boolean getLock(String key,int expire){
        boolean getLock=false;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            if (jedis != null) {
                if(jedis.setnx(key,"1")>0){
                    jedis.expire(key,expire);
                    getLock=true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != jedis)
                jedis.close();
        }
        return getLock;
    }

    public static boolean getLockSafe(String key,int expire) {
        boolean getLock = false;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            if (jedis != null) {
                long value = System.currentTimeMillis() + expire;
                if (jedis.setnx(key, String.valueOf(value)) > 0) {
                    jedis.expire(key, expire);
                    getLock = true;
                } else {
                    //没有获取到锁
                    String oldExpire = jedis.get(key);

                    if (oldExpire != null) {
                        oldExpire = "0";
                    }
                    long oldExpireTime = Long.parseLong(oldExpire);
                    if (oldExpireTime < System.currentTimeMillis()) {
                        //过期时间小于当前时间,则说明可以重新获取到锁
                        jedis.del(key);
                        long newvalue = System.currentTimeMillis() + expire;
                        if (jedis.setnx(key, String.valueOf(newvalue)) > 0) {
                            jedis.expire(key, expire);
                            getLock = true;
                        }
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != jedis)
                jedis.close();
        }
        return getLock;
    }



    public static boolean unLock(String key){
        boolean getLock=false;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            if (jedis != null) {
                jedis.del(key);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != jedis)
                jedis.close();
        }
        return getLock;
    }

    public static boolean unLockSafe(String key){
        boolean getLock=false;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            if (jedis != null) {
                String oldExpire = jedis.get(key);
                if (oldExpire != null) {
                    oldExpire = "0";
                }
                long oldExpireTime = Long.parseLong(oldExpire);
                if (oldExpireTime > System.currentTimeMillis()) {
                    jedis.del(key);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != jedis)
                jedis.close();
        }
        return getLock;
    }


    public static void set(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            if (jedis != null)
                jedis.set(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != jedis)
                jedis.close();
        }
    }
    public static void setset(String key, String value,int pexpireSeconds) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            if (jedis != null) {
                jedis.sadd(key, value);
                if(pexpireSeconds>0){
                    jedis.pexpire(key,pexpireSeconds*1000);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != jedis)
                jedis.close();
        }
    }

    public static void setex(String key,int se, String value) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            if (jedis != null) {
                jedis.setex(key,se,value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != jedis)
                jedis.close();
        }
    }

    public static String get(String key) {
        Jedis jedis = null;
        String value = null;
        try {
            jedis = getJedis();
            if (jedis != null)

                value = jedis.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != jedis)
                jedis.close();
        }
        return value;
    }
    public static Set<String> getSetList(String key) {
        Jedis jedis = null;
        Set<String> value = null;
        try {
            jedis = getJedis();
            if (jedis != null)
                value = jedis.smembers(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != jedis)
                jedis.close();
        }
        return value;
    }
    public static long getSetCount(String key) {
        Jedis jedis = null;
        long value = 0;
        try {
            jedis = getJedis();
            if (jedis != null) {
                value = jedis.scard(key);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != jedis)
                jedis.close();
        }
        return value;
    }
    public static boolean existstSet(String key,String value) {
        Jedis jedis = null;
        boolean r = false;
        try {
            jedis = getJedis();
            if (jedis != null) {
                r = jedis.sismember(key, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != jedis)
                jedis.close();
        }
        return r;
    }
}
