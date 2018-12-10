package com.taotao.content.service;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

import java.util.List;

/**
 * @Auther: DELL
 * @Date: 2018/11/30 16:33
 * @Description:
 */
public interface ContentService {
    public EasyUIDataGridResult queryContentList(Long categoryId,Integer page,Integer rows);

    TaotaoResult saveContent(TbContent tbContent);

    TaotaoResult deleteContent(Long params);

    TaotaoResult editContent(TbContent tbContent);
    List<TbContent> getConterById(Long categoryId);
}
