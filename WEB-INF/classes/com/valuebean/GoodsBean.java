package com.valuebean;

public class GoodsBean {
	private int 	id;								
	private String 	goodsViewpic;					
	private String 	goodsName;						
	private float 	goodsPrice;						
	private int 	goodsStoreNum;					
	private int		goodsBuyNum;					
	private String 	goodsStocktime;					
	private String 	goodsInfo;						
	private String 	goodsMaker;						
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}	
	public String getGoodsViewpic() {
		return goodsViewpic;
	}
	public void setGoodsViewpic(String goodsViewpic) {
		this.goodsViewpic = goodsViewpic;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public float getGoodsPrice() {
		return goodsPrice;
	}
	public void setGoodsPrice(float goodsPrice) {
		this.goodsPrice = goodsPrice;
	}
	public int getGoodsStoreNum() {
		return goodsStoreNum;
	}
	public void setGoodsStoreNum(int goodsStoreNum) {
		this.goodsStoreNum = goodsStoreNum;
	}	
	public int getGoodsBuyNum() {
		return goodsBuyNum;
	}
	public void setGoodsBuyNum(int goodsBuyNum) {
		this.goodsBuyNum = goodsBuyNum;
	}
	public String getGoodsStocktime() {
		return goodsStocktime;
	}
	public void setGoodsStocktime(String goodsStocktime) {
		this.goodsStocktime = goodsStocktime;
	}
	public String getGoodsInfo() {
		return goodsInfo;
	}
	public void setGoodsInfo(String goodsInfo) {
		this.goodsInfo = goodsInfo;
	}
	public String getGoodsMaker() {
		return goodsMaker;
	}
	public void setGoodsMaker(String goodsMaker) {
		this.goodsMaker = goodsMaker;
	}	
	public float getGoodsMoney(){
		return Math.round(goodsPrice*goodsBuyNum*100)/100f;
	}
}
