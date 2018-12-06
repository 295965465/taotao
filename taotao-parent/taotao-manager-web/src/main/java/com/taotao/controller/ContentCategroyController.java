package com.taotao.controller;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.content.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ContentCategroyController {
    @Autowired
    private ContentCategoryService service;


    /**
     * 查询
     * @param parentId
     * @return
     */
    @RequestMapping(value="/content/category/list",method = RequestMethod.GET)
    @ResponseBody
    public List<EasyUITreeNode> getContentCategroyList(@RequestParam(value="id",defaultValue = "0") Long parentId){
        return  service.getContentCategoryList(parentId);
    }

    /**
     * 新建
     * @param parentId
     * @param name
     * @return
     */
    @RequestMapping(value = "/content/category/create",method =RequestMethod.POST)
    @ResponseBody
    public TaotaoResult creatContentCategroy(Long parentId,String name){
        return service.creatContentCategory(parentId, name);

    }

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping(value = "/content/category/delete/",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult deleteContentCategory(Long id){
        return service.deleteContentCategory(id);
    }

    /**
     * 更新
     * @param id
     * @param name
     */
    @RequestMapping(value = "/content/category/update",method = RequestMethod.POST)
    @ResponseBody
    public void updateContentCategory(Long id,String name){
        service.updateContentCategory(id,name);
    }
}
