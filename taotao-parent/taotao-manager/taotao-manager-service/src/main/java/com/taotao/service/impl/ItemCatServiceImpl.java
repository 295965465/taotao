package com.taotao.service.impl;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.mapper.TbItemCatMapper;
import com.taotao.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        return null;
    }
}
