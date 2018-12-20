package com.taotao.search.exception;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: DELL
 * @Date: 2018/12/20 13:54
 * @Description:
 */
public class GlobalException implements HandlerExceptionResolver {
    Logger logger =Logger.getLogger(GlobalException.class);
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {

        //写日志文件
        logger.error(e.getMessage(),e);
        //发邮件,发短信
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject("message","系统发生异常,请稍后再试");
        modelAndView.setViewName("error/exception");
        return modelAndView;
    }
}
