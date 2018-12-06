package com.taotao.controller;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Auther: DELL
 * @Date: 2018/12/6 11:52
 * @Description:
 */
@Controller
public class ItemCatController {

    @Autowired
    ItemCatService itemCatService;


    @RequestMapping(value = "/item/cat/list")
    @ResponseBody
    public List<EasyUITreeNode>  getItemCatList(@RequestParam(value = "id",defaultValue = "0") long parentId){
        List<EasyUITreeNode> list=itemCatService.getItemCatListByParentId(parentId);
        return list;
    }

}
