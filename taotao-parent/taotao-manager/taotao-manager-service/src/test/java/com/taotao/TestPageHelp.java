package com.taotao;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemExample;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class TestPageHelp {

    @Test
    public void testHelp(){
        //1.初始化Spring容器
        ApplicationContext applicationContext=new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
        //2.获取mapper代理对象
        TbItemMapper tbItemMapper =  applicationContext.getBean(TbItemMapper.class);
        //3.设置分页信息
        PageHelper.startPage(1,3);//紧跟的第一个查询才会被分页
        //4.通过mapper查询数据库得到数据
        TbItemExample example =new TbItemExample();
        List<TbItem> list= tbItemMapper.selectByExample(example);
        List<TbItem> list1= tbItemMapper.selectByExample(example);
        //获取分页信息
        PageInfo<TbItem> pageInfo=new PageInfo<>(list);
        System.out.println("第一个list的长度"+list.size());
        System.out.println("第一个list的长度"+list1.size());
        /*for (TbItem tbItem : list1) {
            System.out.println(tbItem.getId());
            
        }*/

    }
}
