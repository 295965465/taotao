package com.taotao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
    @RequestMapping("/")
    public String ShowIndex(){
        return  "index";
    }




    //显示商品list信息
    @RequestMapping("/{page}")
    public  String ShowPage(@PathVariable String page){
        return page;

    }


}
