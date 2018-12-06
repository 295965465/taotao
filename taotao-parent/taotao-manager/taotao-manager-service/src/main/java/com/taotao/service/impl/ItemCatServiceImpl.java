package com.taotao.service.impl;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: DELL
 * @Date: 2018/12/4 14:04
 * @Description:
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {
    @Autowired
    TbItemCatMapper catMapper;
    @Override
    public List<EasyUITreeNode> getItemCatListByParentId(Long parentId) {
        TbItemCatExample tbItemCatExample = new TbItemCatExample();
        TbItemCatExample.Criteria criteria=tbItemCatExample.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        //返回list集合
        List<TbItemCat> list=catMapper.selectByExample(tbItemCatExample);
        List<EasyUITreeNode> easyUITreeNodeList=new ArrayList<>();

        for (TbItemCat tbItemCat : list) {
            EasyUITreeNode easyUITreeNode=new EasyUITreeNode();
            easyUITreeNode.setId(tbItemCat.getId());
            easyUITreeNode.setText(tbItemCat.getName());
            easyUITreeNode.setState(tbItemCat.getIsParent()?"closed":"open");
            easyUITreeNodeList.add(easyUITreeNode);

        }

        return easyUITreeNodeList;
    }
}
