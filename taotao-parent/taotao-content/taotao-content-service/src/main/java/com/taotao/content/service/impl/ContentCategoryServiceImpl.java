package com.taotao.content.service.impl;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.content.service.ContentCategoryService;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {
    @Autowired
    TbContentCategoryMapper TbContentCategoryMapper;
    @Override
    public List<EasyUITreeNode> getContentCategoryList(Long parentId) {
        //1.注入mapping
        //2.创建example
        TbContentCategoryExample tbContentCategoryExample = new TbContentCategoryExample();
        //3.设置条件
        TbContentCategoryExample.Criteria criteria =tbContentCategoryExample.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        //4.执行查询
        List<TbContentCategory> list=TbContentCategoryMapper.selectByExample(tbContentCategoryExample);
        //5.转为easyUITreeNode列表
        List<EasyUITreeNode> easyUITreeNodeList=new ArrayList<>();
        for (TbContentCategory tbContentCategory : list) {
            EasyUITreeNode easyUITreeNode=new EasyUITreeNode();
            easyUITreeNode.setId(tbContentCategory.getId());
            easyUITreeNode.setState(tbContentCategory.getIsParent()?"closed":"open");
            //分类名称
            easyUITreeNode.setText(tbContentCategory.getName());
            easyUITreeNodeList.add(easyUITreeNode);

        }
        //6.返回

        return easyUITreeNodeList;
    }

    @Override
    public TaotaoResult creatContentCategory(Long parentId, String name) {
        //1.构件一个对象
         TbContentCategory tbc=new TbContentCategory();
         tbc.setCreated(new Date());
         tbc.setIsParent(false);
         tbc.setName(name);
         tbc.setParentId(parentId);
         tbc.setSortOrder(1);
         tbc.setStatus(1);
         tbc.setUpdated(tbc.getUpdated());
        //2.插入TBContentCategory表
        TbContentCategoryMapper.insert(tbc);
        //判断如果添加的节点的父节点本身为叶子节点，则更新为父节点
        TbContentCategory parent=TbContentCategoryMapper.selectByPrimaryKey(parentId);
        if (parent.getIsParent()==false){
            parent.setIsParent(true);
            TbContentCategoryMapper.updateByPrimaryKeySelective(parent);
        }
        //3.返回这个数据的id  需要主键返回
        return TaotaoResult.ok(tbc);
    }

    @Override
    public TaotaoResult deleteContentCategory(Long Id) {
        //判断是不是父节点
        TbContentCategory nowNode=TbContentCategoryMapper.selectByPrimaryKey(Id);
        if (nowNode.getIsParent()==false){
            //删除当前节点
            TbContentCategoryMapper.deleteByPrimaryKey(Id);
            //获取当前节点的父节点ID
            Long parentId=nowNode.getParentId();
            //通父节点ID查询他还有没有字节点
            TbContentCategoryExample example=new TbContentCategoryExample();
            TbContentCategoryExample.Criteria criteria =example.createCriteria();
            criteria.andParentIdEqualTo(parentId);
            List<TbContentCategory> list=  TbContentCategoryMapper.selectByExample(example);
            //如果已经没有子节点了,这把父节点更新成子节点
            if (list.size()==0){
                TbContentCategory parent=TbContentCategoryMapper.selectByPrimaryKey(parentId);
                parent.setIsParent(false);
                TbContentCategoryMapper.updateByPrimaryKeySelective(parent);
            }
            return  TaotaoResult.ok();
        }
        return TaotaoResult.build(400,"不能删父节点");
    }

    @Override
    public void updateContentCategory(Long id, String name) {
        TbContentCategory tbc=new TbContentCategory();
        tbc.setId(id);
        tbc.setName(name);
        tbc.setUpdated(new Date());
        TbContentCategoryMapper.updateByPrimaryKeySelective(tbc);
    }
}
