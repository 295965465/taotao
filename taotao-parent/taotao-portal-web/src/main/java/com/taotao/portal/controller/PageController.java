package com.taotao.portal.controller;

import com.taotao.common.util.JsonUtils;
import com.taotao.content.service.ContentService;
import com.taotao.pojo.TbContent;
import com.taotao.portal.pojo.Ad1Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * 展示页面
 */
@Controller
public class PageController {
    @Autowired
    ContentService contentService;
    @Value("${AD1_CATEGORY_ID}")
    private  Long AD1;
    @Value("${AD1_HEIGHT_B}")
    private String heightB;
    @Value("${AD1_HEIGHT}")
    private String height;
    @Value("${AD1_WIDTH}")
    private String width;
    @Value("${AD1_WIDTH_B}")
    private String widthB;
    @RequestMapping("/index")
    public String ShowIndex(Model model){
        List<TbContent> list=contentService.getConterById(AD1);
        //转换给自定义的POJO格式
        List<Ad1Node> ad1NodeList=new ArrayList<>();
        for (TbContent tbContent : list) {
            Ad1Node ad1Node=new Ad1Node();
            ad1Node.setAlt(tbContent.getSubTitle());
            ad1Node.setHeight(height);
            ad1Node.setHeightB(heightB);
            ad1Node.setHref(tbContent.getUrl());
            ad1Node.setSrc(tbContent.getPic());
            ad1Node.setSrcB(tbContent.getPic2());
            ad1Node.setWidth(width);
            ad1Node.setWidthB(widthB);
            ad1NodeList.add(ad1Node);
        }
        model.addAttribute("ad1", JsonUtils.objectToJson(ad1NodeList));
        return "index";
    }

}
