package com.taotao.content.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.content.service.ContentService;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import org.springframework.beans.factory.annotation.Autowired;
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
    TbContentMapper tbContentMapper;
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
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult deleteContent(Long params) {
        int rows=tbContentMapper.deleteByPrimaryKey(params);
        if (rows==0){
            return TaotaoResult.build(400,"删除失败");
        }
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult editContent(TbContent tbContent) {
        tbContent.setUpdated(new Date());
        int rows=tbContentMapper.updateByPrimaryKey(tbContent);

        if (rows==0){
            return TaotaoResult.build(400,"数据更新失败");
        }
        return TaotaoResult.ok();
    }
}
