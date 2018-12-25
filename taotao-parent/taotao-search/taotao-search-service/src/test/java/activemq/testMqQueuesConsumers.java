package activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

import javax.jms.*;

/**
 * @Auther: DELL
 * @Date: 2018/12/21 17:43
 * @Description:
 */
public class testMqQueuesConsumers {


    @Test
    public  void recieve() throws  Exception {
        //1.创建连接工厂
        ConnectionFactory factory=new ActiveMQConnectionFactory("tcp://192.168.160.128:61616");
        //2.创建连接
        Connection connection=factory.createConnection();
        //3.开启连接
        connection.start();
        //4.创建session
        Session session=connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        //5.创建一个接受消息的目的地
        Queue queue=session.createQueue("queue-test");
        //6.创建消费者
        MessageConsumer consumer=session.createConsumer(queue);
        //7.接受消息
        //MessageListener listener;
        consumer.setMessageListener(new MessageListener() {

            @Override
            public void onMessage(Message message) {
                if (message instanceof TextMessage){
                    TextMessage message1=(TextMessage) message;
                    try {
                        System.out.println("收到："+message1.getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        Thread.sleep(199999);
        //8.关闭连接
        consumer.close();
        session.close();
        connection.close();

    }
}
