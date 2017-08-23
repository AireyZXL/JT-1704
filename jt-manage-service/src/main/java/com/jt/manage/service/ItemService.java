/**
 * 
 */
package com.jt.manage.service;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jt.common.service.BaseService;
import com.jt.common.service.RedisService;
import com.jt.common.spring.exetend.PropertyConfig;
import com.jt.common.vo.EasyUIResult;
import com.jt.manage.mapper.ItemDescMapper;
import com.jt.manage.mapper.ItemMapper;
import com.jt.manage.pojo.Item;
import com.jt.manage.pojo.ItemDesc;

/**
 * @author Airey
 * @date 2017年7月22日
 */
@Service
public class ItemService extends BaseService<Item> {
	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private ItemDescMapper itemDescMapper;

	@Autowired
	private RedisService redisService;
	@PropertyConfig
	private String ITEM_KEY_PREFIX;
    @Autowired
	private RabbitTemplate rabbitTemplate;

	/**
	 * {"title":2000,"rows":[{},{},{}]} title 是记录总数 rows 表示查询的数据
	 * 
	 * @param page
	 * @param rows
	 * @return
	 * @throws JsonProcessingException
	 */
	public EasyUIResult findItemList(int page, int rows) {
		// 使用分页插件进行分页 page:查询的页数 rows 查询的数据量
		// 分页的开关
		PageHelper.startPage(page, rows);
		List<Item> itemList = itemMapper.findItemList();
		// 自己计算全部的信息数
		PageInfo<Item> info = new PageInfo<Item>(itemList);
		return new EasyUIResult(info.getTotal(), info.getList());

		/*
		 * 手动的分页配置 int title = itemMapper.selectItemCount(); // 分页开始的行数 int
		 * startNum = (page - 1) * rows; List<Item> itemList =
		 * itemMapper.findPageInfoList(startNum, rows);
		 * System.out.println(itemList); EasyUIResult result = new
		 * EasyUIResult(); result.setTotal(title); result.setRows(itemList);
		 * return result;
		 */
	}

	/**
	 * @param itemCatId
	 * @return
	 */
	public String findItemCatName(Long itemCatId) {
		return itemMapper.findItemCatName(itemCatId);
	}

	/**
	 * 查询全部的数据记录
	 * 
	 * @return
	 */
	public int findCount() {
		return itemMapper.findCount();
	}

	/**
	 * 新增商品信息
	 * 
	 * @param item
	 * @param desc 
	 */
	public void saveItem(Item item, String desc) {
		item.setCreated(new Date());
		item.setUpdated(item.getCreated());
		// 动态插入
		itemMapper.insertSelective(item);
		/**
		 * 问题：商品描述信息中需要进行入库操作，但是入库的Id主键应该是商品Id,
		 * 但是,现在商品处于要插入状态，mysql还没有为其分配Id值，所有现在的操作
		 * 拿不到Id
		 * 解决方案：
		 * mybatis+mysql的具体实现，如果换成Oracle则不能正常运行
		 * itemMapper.insertSelective(item)后
		 * 又自己查询了新增的数据。
		 * 通用mapper将数据插入之后，再次查询最大Id值，赋值给Item对象
		 */

		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(item.getId());
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(item.getCreated());
		itemDesc.setUpdated(item.getUpdated());
		itemDescMapper.insertSelective(itemDesc);
	}

	/**
	 * 修改商品信息
	 * 
	 * @param item
	 * @param desc 
	 */
	public void updateItem(Item item, String desc) {
		item.setUpdated(new Date());
		itemMapper.updateByPrimaryKeySelective(item);
		// 修改商品描述信息
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(item.getId());
		itemDesc.setItemDesc(desc);
		itemDesc.setUpdated(item.getUpdated());
		itemDescMapper.updateByPrimaryKeySelective(itemDesc);
		// 更新缓存
		// redisService.del(ITEM_KEY_PREFIX + item.getId());
		// 发送消息MQ中消息的接受着去处理更新缓存（删除）
		String routingKey = "item.update";// 定义，全局唯一
		rabbitTemplate.convertAndSend(routingKey, ITEM_KEY_PREFIX + item.getId());
		//System.out.println(rabbitTemplate);
	}

	/**
	 * 删除商品信息
	 * 
	 * @param ids
	 */
	public void deleteItem(Long[] ids) {
		itemMapper.deleteByIDS(ids);
		// 删除商品详细信息
		itemDescMapper.deleteByIDS(ids);
	}

	/**
	 * 商品下架
	 * 
	 * @param ids
	 */
	public void instockItem(Long[] ids) {

		for (Long id : ids) {
			Item item = new Item();
			item.setId(id);
			item.setStatus(2);
			item.setUpdated(new Date());
			itemMapper.updateByPrimaryKeySelective(item);
		}
	}

	/**
	 * 商品上架
	 * 
	 * @param ids
	 */
	public void reshelfItem(Long[] ids) {
		for (Long id : ids) {
			Item item = new Item();
			item.setId(id);
			item.setStatus(1);
			item.setUpdated(new Date());
			itemMapper.updateByPrimaryKeySelective(item);
		}
	}

	/**
	 * 查询商品描述信息
	 * @param itemId
	 * @return
	 */
	public ItemDesc findItemDesc(Long itemId) {

		return itemDescMapper.selectByPrimaryKey(itemId);
	}

	/**
	 * 根据ItemId获取ItemDesc对象，把外键当做主键
	 * @param itemId
	 * @return
	 */
	public ItemDesc getItemDescById(Long itemId) {
		return itemDescMapper.selectByPrimaryKey(itemId);
	}

}
