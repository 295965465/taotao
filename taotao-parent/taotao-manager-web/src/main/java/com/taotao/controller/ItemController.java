package com.taotao.controller;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.util.JsonUtils;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;
import com.taotao.web.util.FastDFSClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EasyUIDataGridResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;


@Controller
public class ItemController {
	@Value("${TAOTAO_IMAGE_SERVER_URL}")
	private String TAOTAO_IMAGE_SERVER_URL;
	@Autowired
	private ItemService itemservice;
	@RequestMapping(value="/item/list",method=RequestMethod.GET)
	@ResponseBody
	public EasyUIDataGridResult getItemList(Integer page,Integer rows){
		//1.引入服务
		//2.注入服务
		//3.调用服务的方法
		return itemservice.getItemList(page, rows);
	}
	//produces:就可以设置content-type
    @RequestMapping(value = "/pic/upload",produces = MediaType.TEXT_PLAIN_VALUE+";charset=utf-8")
	@ResponseBody
	public String uploadImage(MultipartFile uploadFile){
		//获取源文件的扩展名
		try {
			String originalFilename=uploadFile.getOriginalFilename();
			String extname=originalFilename.substring(originalFilename.lastIndexOf(".")+1);
			//获取源文件的字节数组
			byte[] bytes=uploadFile.getBytes();
			//通过fastdfsclient的方法上传图片(差数是字节数组和扩展名 不包含.)
			FastDFSClient fastDFSClient=new FastDFSClient("classpath:resource/fastdfs.conf");
			//返回值 就是url图片的地址
			String String=fastDFSClient.uploadFile(bytes,extname);
			String path=TAOTAO_IMAGE_SERVER_URL+String;
			//如果成功这返回map
			Map<String,Object> map=new HashMap<>();
			map.put("error",0);
			map.put("url",path);
			return JsonUtils.objectToJson(map);

		} catch (Exception e) {
			e.printStackTrace();
			Map<String,Object> map=new HashMap<>();
			map.put("error",1);
			map.put("message","上传失败");
			return JsonUtils.objectToJson(map);
		}

	}
	/**
	 *
	 * @param item
	 * @param desc  是item 描述 插入item-desc这张表
	 * @return
	 */
	@RequestMapping(value = "/item/save",method = RequestMethod.POST)
	@ResponseBody
	public TaotaoResult creatItem(TbItem item,String desc){

		return itemservice.saveItem(item,desc);
	}

}
