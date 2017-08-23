package com.jt.web.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jt.common.po.BasePojo;
/**
 * 
 * @author Airey
 * @date   2017年7月22日
 */
//商品信息
//忽略掉solr索引field和Java对象的属性不匹配的字段
@JsonIgnoreProperties(ignoreUnknown=true)
public class Item extends BasePojo {
	
	private Long id;// 商品id
	private String title;// 商品标题
	private String sellPoint;// 卖点
	private Long price;// 商品价格 (后期由js计算价格/100)
	private Integer num;// 商品数量
	private String barcode;// 扫描码
	private String image;// 商品图片
	private Long cid;// 分类号
	private Integer status;// 状态 默认值为1，可选值：1正常，2下架，3删除

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSellPoint() {
		return sellPoint;
	}

	public void setSellPoint(String sellPoint) {
		this.sellPoint = sellPoint;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Long getCid() {
		return cid;
	}

	public void setCid(Long cid) {
		this.cid = cid;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	//页面要直接拆分图片，所有需要构建这个方法，无需set方法
	public String[] getImages(){
			return this.image.split(",");
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", title=" + title + ", sellPoint=" + sellPoint + ", price=" + price + ", num=" + num
				+ ", barcode=" + barcode + ", image=" + image + ", cid=" + cid + ", status=" + status + "]";
	}


}
