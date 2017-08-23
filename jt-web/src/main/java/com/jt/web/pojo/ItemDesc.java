/**
 * 
 */
package com.jt.web.pojo;

import com.jt.common.po.BasePojo;

/**
 * @author Airey
 * @date   2017年8月4日
 */
public class ItemDesc extends BasePojo {
	private Long itemId; // 新增商品时要设置它的id
	private String itemDesc;

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
}
