package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.util.IDUtils;
import com.taotao.common.util.JsonUtils;
import com.taotao.manager.redis.JedisClient;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.pojo.TbItemDesc;
import com.taotao.service.ItemService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemExample;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

@Service
public class ItemServiceImpl implements ItemService {
@Autowired
private TbItemMapper mapper;
@Autowired
private TbItemDescMapper itemDescMapper;
@Autowired
private JmsTemplate  jmsTemplate;
@Autowired
private JedisClient jedisClient;
@Resource(name = "topicDestination")
private Destination  destination;
@Value("${ITEM_INFO_KEY}")
private String ITEM_INFO_KEY;
@Value("${Longtime}")
private Integer Longtime;
private Logger logger=Logger.getLogger(ItemServiceImpl.class);
	@Override
	public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
		//1.设置分页的信息 使用pagehelper
		if(page==null) {
			page=1;
		}
		if(rows==null) {
			rows=30;
		}
		PageHelper.startPage(page, rows);
		//2.注入mapper
		//3.创建example 对象 不需要设置查询条件
		TbItemExample example = new TbItemExample();
		//4.根据mapper调用查询所有数据的方法
		List<TbItem> list = mapper.selectByExample(example);
		//5.获取分页的信息
		PageInfo<TbItem> info = new PageInfo<>(list);
		//6.封装到EasyUIDataGridResult
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setTotal((int)info.getTotal());
		result.setRows(info.getList());
		//7.返回
		return result;
	}

	@Override
	public TaotaoResult saveItem(final TbItem item, String desc) {
        //补全TbItem中的信息
		final Long itemId= IDUtils.genItemId();
		item.setId(itemId);
		item.setCreated(new Date());
		//1-正常，2-下架，3-删除',
		item.setStatus((byte) 1);
		item.setUpdated(item.getCreated());
		mapper.insertSelective(item);
		//3.补全商品描述中的属性
		TbItemDesc itemDesc=new TbItemDesc();
		itemDesc.setCreated(new Date());
		itemDesc.setItemId(itemId);
		itemDesc.setItemDesc(desc);
		itemDesc.setUpdated(itemDesc.getCreated());
		itemDescMapper.insert(itemDesc);
		//添加发送消息的逻辑
		ItemServiceImpl a =new ItemServiceImpl();
		a.sendMessage(itemId);
		return TaotaoResult.ok();
	}
    public  void sendMessage(final Long itemId){
		jmsTemplate.send(destination, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(itemId.toString());
			}
		});
	}
	@Override
	public TaotaoResult deleteItem(Long ids) {
		int rows=mapper.deleteByPrimaryKey(ids);
		if (rows>0){
			return  TaotaoResult.ok();
		}{
			return  TaotaoResult.build(400,"删除失败");
		}


	}

	@Override
	public TaotaoResult updateItem(TbItem item, String desc) {
		//1.更改item中的update时间
		item.setUpdated(new Date());
		int rows=mapper.updateByPrimaryKeySelective(item);
		//3.补全商品描述中的属性
		TbItemDesc itemDesc=new TbItemDesc();
		itemDesc.setUpdated(new Date());
		itemDesc.setItemDesc(desc);
		itemDesc.setItemId(item.getId());
		int rows1=itemDescMapper.updateByPrimaryKeySelective(itemDesc);
		if (rows>0&&rows1>0){
			return  TaotaoResult.ok();
		}{
			return  TaotaoResult.build(400,"删除失败");
		}
	}

	@Override
	public TaotaoResult reshelfItem(Long ids) {
		TbItem tbItem=new TbItem();
		tbItem.setId(ids);
		tbItem.setStatus((byte)1);
		tbItem.setUpdated(new Date());
		mapper.updateByPrimaryKeySelective(tbItem);
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult instockItem(Long ids) {
		TbItem tbItem=new TbItem();
		tbItem.setId(ids);
		tbItem.setStatus((byte)2);
		tbItem.setUpdated(new Date());
		mapper.updateByPrimaryKeySelective(tbItem);
		return TaotaoResult.ok();
	}

	@Override
	public TbItem getItemById(Long itemId) {
        //1.添加缓存要先看缓存中有没有这个数据
		String reidsKey=ITEM_INFO_KEY+":"+itemId+":BASE";
		String jsonStr=jedisClient.get(reidsKey);
		if (StringUtils.isNotBlank(jsonStr)){
			logger.warn(reidsKey+"现在已经有缓存了");
			jedisClient.expire(reidsKey,3600*24);
			return JsonUtils.jsonToPojo(jsonStr,TbItem.class);

		}
		TbItem tbItem = mapper.selectByPrimaryKey(itemId);
        //2.添加缓存
		jedisClient.set(reidsKey, JsonUtils.objectToJson(tbItem));
		jedisClient.expire(reidsKey,3600*24);
		logger.warn(reidsKey+"现在还没有缓存");
		return tbItem;
	}

	@Override
	public TbItemDesc getItemDescById(Long itemId) {
		String reidsKey=ITEM_INFO_KEY+":"+itemId+":DESC";
		String jsonStr=jedisClient.get(reidsKey);
		if (StringUtils.isNotBlank(jsonStr)){
			logger.warn(reidsKey+"现在已经有缓存了");
			jedisClient.expire(reidsKey,3600*24);
			return JsonUtils.jsonToPojo(jsonStr,TbItemDesc.class);

		}
		TbItemDesc tbItemDesc = itemDescMapper.selectByPrimaryKey(itemId);
		jedisClient.set(reidsKey, JsonUtils.objectToJson(tbItemDesc));
		jedisClient.expire(reidsKey,3600*24);
		logger.warn(reidsKey+"Desc现在还没有缓存");
		return tbItemDesc;
	}

}
