package com.taotao.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.mapper.TestMapper;
import com.taotao.service.TestService;

public class TestServiceImpl implements TestService {
	@Autowired
	private TestMapper mapper;
	@Override
	public String queryNow() {
		//1.注入mapper 
		//2.调用mapper的方法 返回
		String a=mapper.queryNow();
		//System.out.println(a);
		return a;
	}

}
