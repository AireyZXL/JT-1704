/**
 * 
 */
package com.jt.manage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jt.common.mapper.SysMapper;
import com.jt.manage.pojo.Item;

/**
 * @author Airey
 * @date 2017年7月22日
 */
public interface ItemMapper extends SysMapper<Item> {
	// 查询全部的商品信息，根据日期倒叙排列
	public List<Item> findItemList();

	/**
	 * @return
	 */
	public int selectItemCount();

	/**
	 * @param startNum
	 * @param rows
	 * @return
	 */
	public List<Item> findPageInfoList(@Param("startNum") int startNum, @Param("rows") int rows);

	/**
	 * @param itemCatId
	 * @return
	 */
	public String findItemCatName(Long itemCatId);
}
