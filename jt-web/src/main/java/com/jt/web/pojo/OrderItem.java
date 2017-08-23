/**
 * 
 */
package com.jt.web.pojo;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Airey
 * @date   2017年8月10日
 */
public class OrderItem implements Serializable {
   //联合主键
	private String itemId;
	private String orderId;
	private Integer num;
	private String title;
	private Long price;
	private String totalFee;
	private String picPath;

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public String getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}

	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	@Override
	public String toString() {
		return "OrderItem [itemId=" + itemId + ", orderId=" + orderId + ", num=" + num + ", title=" + title + ", price="
				+ price + ", totalFee=" + totalFee + ", picPath=" + picPath + "]";
	}

}
