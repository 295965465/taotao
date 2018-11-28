package com.taotao.controller;

import com.taotao.common.pojo.EasyUITreeNode;
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



    @RequestMapping(value="/content/category/list",method = RequestMethod.GET)
    @ResponseBody
    public List<EasyUITreeNode> getContentCategroyList(@RequestParam(value="id",defaultValue = "0") Long parentId){
        return  service.getContentCategoryList(parentId);
    }
}
