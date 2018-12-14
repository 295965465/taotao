import org.junit.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.util.HashSet;
import java.util.Set;

/**
 * @Auther: DELL
 * @Date: 2018/12/13 14:03
 * @Description: redis 测试
 */
public class JedisTest {
    /**
     * 单机版测试
     */
    @Test
    public void testAlone() {
        //1.创建连接端口.Ip地址
        Jedis jedis = new Jedis("192.168.160.128", 6379);
        //2.放值，取值
        jedis.set("a", "123");
        String value = jedis.get("a");
        System.out.println(value);

        //3.关闭redis连接
        jedis.close();
    }

    /**
     * 连接池
     */
    @Test
    public void testPool() {
        JedisPool jedisPool = new JedisPool("192.168.160.128", 6379);
        Jedis jedis = jedisPool.getResource();
        String value = jedis.get("a");
        System.out.println(value);
        jedis.close();
        jedisPool.close();
    }

    /**
     * 集群版本
     */
    @Test
    public void jedisCluter() {
        Set<HostAndPort> node = new HashSet<>();
        node.add(new HostAndPort("192.168.160.128", 7001));
        node.add(new HostAndPort("192.168.160.128", 7002));
        node.add(new HostAndPort("192.168.160.128", 7003));
        node.add(new HostAndPort("192.168.160.128", 7004));
        node.add(new HostAndPort("192.168.160.128", 7005));
        node.add(new HostAndPort("192.168.160.128", 7006));
        JedisCluster jedisCluster=new JedisCluster(node);
        jedisCluster.set("test","testt");
        System.out.println(jedisCluster.get("test"));
        jedisCluster.close();
    }
}
