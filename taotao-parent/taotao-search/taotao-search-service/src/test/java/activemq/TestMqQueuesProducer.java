package activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

import javax.jms.*;

/**
 * @Auther: DELL
 * @Date: 2018/12/21 17:22
 * @Description:
 */
public class TestMqQueuesProducer{
    @Test
    public void send()  throws Exception{
        //1.创建一个连接工厂connectionfactory
        ConnectionFactory factory=new ActiveMQConnectionFactory("tcp://192.168.160.128:61616");
        //2.通过工厂获取连接对象
        Connection connection=factory.createConnection();
        //3.开启连接
        connection.start();
        //4.创建一个session对象 提供发送消息等方法
        //第一个参数：表示是否开启分布式事务（JTA）一般都是false.
        //第二个参数：就是设置消息的应答模式,如果第一个参数设置为false,第二个参数设置才有意义，一般用自动应答.
        Session session=connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        //5.创建目的地
        Queue queue=session.createQueue("queue-test");
        //6.创建生产者
        MessageProducer producer=session.createProducer(queue);
        //7.构建消息的内容
        TextMessage textMessage = session.createTextMessage("我是测试");
        //8.发送消息
        producer.send(textMessage);
        //9.关闭连接

        producer.close();
        session.close();
        connection.close();

    }

}
