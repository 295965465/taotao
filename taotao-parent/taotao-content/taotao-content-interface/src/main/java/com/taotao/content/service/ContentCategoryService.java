package com.taotao.content.service;

import com.taotao.common.pojo.EasyUITreeNode;

import java.util.List;

public interface ContentCategoryService {


    /**
     * 通过父节点得到子节点的集合
     * @param parentId
     * @return
     */
    public List<EasyUITreeNode> getContentCategoryList(Long parentId);
}
