package com.taotao.content.service.impl;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.content.service.ContentCategoryService;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
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
}
