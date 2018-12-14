import com.taotao.content.redis.JedisClient;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Auther: DELL
 * @Date: 2018/12/13 16:57
 * @Description:
 */

public class jedisTestForSpring {

    @Test
    public void testJedisPool() {
         //1.初始化spring容器
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-redis.xml");
        //2.获取实现类实例
        JedisClient bean = context.getBean(JedisClient.class);

        //3.调用方法操作

        System.out.println(bean.get("a"));

    }
}
