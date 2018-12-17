package com.taotao.controller;

import com.taotao.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @Auther: DELL
 * @Date: 2018/12/17 16:29
 * @Description:
 */
@Controller
public class SearchController {
    @Autowired
    private SearchService service;

}
