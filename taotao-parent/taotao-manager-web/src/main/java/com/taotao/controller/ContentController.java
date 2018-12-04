package com.taotao.controller;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.content.service.ContentService;
import com.taotao.pojo.TbContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 这个类是用来实现内容分类节点中的详细内容
 */
@Controller
public class ContentController {
    @Autowired
    ContentService contentService;
    @RequestMapping(value = "/content/query/list",method = RequestMethod.GET)
    @ResponseBody
    public EasyUIDataGridResult queryContentList(Long categoryId,Integer page,Integer rows){
        return  contentService.queryContentList(categoryId,page,rows);
    }
    @RequestMapping(value = "/content/save",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult saveContent(TbContent tbContent){

        return contentService.saveContent(tbContent);
    }
}
