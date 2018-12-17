package com.taotao.controller;


import com.taotao.common.pojo.TaotaoResult;
import com.taotao.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Auther: DELL
 * @Date: 2018/12/17 16:33
 * @Description:
 */
@Controller
public class ImportAllItems {
    @Autowired
    private SearchService service;


    @RequestMapping(value = "/index/importAll",method = RequestMethod.GET)
    @ResponseBody
    public TaotaoResult importAllItems()throws Exception{
        return service.importAllSearchItems();
    }

}
