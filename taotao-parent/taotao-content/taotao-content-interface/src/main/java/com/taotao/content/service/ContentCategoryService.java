package com.taotao.content.service;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;

import java.util.List;

public interface ContentCategoryService {


    /**
     * 通过父节点得到子节点的集合
     * @param parentId
     * @return
     */
    public List<EasyUITreeNode> getContentCategoryList(Long parentId);

    /**
     * 创建内容分类节点
     * @param parentId
     * @param name
     * @return
     */
    public TaotaoResult creatContentCategory(Long parentId,String name);
    /**
     * 删除内容分类节点
     * @param Id
     * @return
     */
    public TaotaoResult deleteContentCategory(Long Id);

    /**
     * 更新内容节点
     * @param id
     * @param name
     */
    public void updateContentCategory(Long id, String name);
}
