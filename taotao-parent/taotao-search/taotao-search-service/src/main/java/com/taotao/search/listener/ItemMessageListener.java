package com.taotao.search.listener;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * @Auther: DELL
 * @Date: 2018/12/25 13:19
 * @Description:获取TbItem类的消息监听
 */
public class ItemMessageListener implements MessageListener {

    @Autowired
    private SearchService service;
    @Override
    public void onMessage(Message message) {
        //判断消息类型是不是textMessage类
        if (message instanceof TextMessage){
            //如果是 获取商品的id
            TextMessage message2 = (TextMessage)message;
            String itemId1;
            try {
                itemId1=message2.getText();
                Long itemId=Long.parseLong(itemId1);
                TaotaoResult taotaoResult = service.updateSearchItemById(itemId);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }






        //
    }
}
