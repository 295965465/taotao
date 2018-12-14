package com.taotao.content.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.util.JsonUtils;
import com.taotao.content.redis.JedisClient;
import com.taotao.content.service.ContentService;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Auther: DELL
 * @Date: 2018/11/30 16:34
 * @Description:
 */
@Service
public class ContenServiceImpl implements ContentService {
    @Autowired
    private TbContentMapper tbContentMapper;
    @Autowired
    private JedisClient jedisClient;
    @Value("${CONTENT_KEY}")
    private String CONTENT_KEY;
    @Override
    public EasyUIDataGridResult queryContentList(Long categoryId, Integer page, Integer rows) {
        if (page==null){
            page=1;
        }
        if (rows==null){
            rows=30;
        }
        PageHelper.startPage(page,rows);

        TbContentExample example=new TbContentExample();
        TbContentExample.Criteria criteria=example.createCriteria();
        criteria.andCategoryIdEqualTo(categoryId);
        List<TbContent> list=tbContentMapper.selectByExample(example);
        PageInfo<TbContent> pageInfo=new PageInfo<>(list);
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setTotal((int)pageInfo.getTotal());
        result.setRows(pageInfo.getList());
        //7.返回
        return result;

    }

    @Override
    public TaotaoResult saveContent(TbContent tbContent) {
        tbContent.setCreated(new Date());
        tbContent.setUpdated(tbContent.getCreated());
       int rows=  tbContentMapper.insertSelective(tbContent);
       if (rows==0){
           return TaotaoResult.build(400,"数据库插入失败");
       }
        //缓存同步
        jedisClient.hdel(CONTENT_KEY, tbContent.getCategoryId().toString());
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult deleteContent(Long params) {
        TbContent tbContent=tbContentMapper.selectByPrimaryKey(params);
        int rows=tbContentMapper.deleteByPrimaryKey(params);
        if (rows==0){
            return TaotaoResult.build(400,"删除失败");
        }
        //缓存同步
        jedisClient.hdel(CONTENT_KEY, tbContent.getCategoryId().toString());
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult editContent(TbContent tbContent) {
        tbContent.setUpdated(new Date());
        int rows=tbContentMapper.updateByPrimaryKey(tbContent);

        if (rows==0){
            return TaotaoResult.build(400,"数据更新失败");
        }
        //缓存同步
        jedisClient.hdel(CONTENT_KEY, tbContent.getCategoryId().toString());

        return TaotaoResult.ok();
    }

    @Override
    public List<TbContent> getConterById(Long categoryId) {
        try {
            //从redis数据库中获取内容分类下的所有的内容。
            String jsonstr = jedisClient.hget(CONTENT_KEY, categoryId+"");
            //如果存在，说明有缓存
            if(StringUtils.isNotBlank(jsonstr)){
                System.out.println("这里有缓存啦！！！！！");
                return JsonUtils.jsonToList(jsonstr, TbContent.class);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        TbContentExample example=new TbContentExample();
        TbContentExample.Criteria criteria=example.createCriteria();
        criteria.andCategoryIdEqualTo(categoryId);
        List<TbContent> list=tbContentMapper.selectByExample(example);
        // 注入jedisclient
        // 调用方法写入redis   key - value
        try {
            System.out.println("没有缓存！！！！！！");
            jedisClient.hset(CONTENT_KEY, categoryId+"", JsonUtils.objectToJson(list));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
