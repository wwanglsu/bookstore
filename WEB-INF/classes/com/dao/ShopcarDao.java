package com.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.toolsbean.DB;
import com.valuebean.GoodsBean;
import com.valuebean.ShopcarBean;

public class ShopcarDao {
	private DB mydb=null;
	
	public ShopcarDao(){
		mydb=new DB();
	}
	public int getGoodsBuyNum(String shopcarid,int goodsid){
		int buynum=0;
		String sql="select shopcar_buygoodscount from tb_shopcar where shopcar_id=? and shopcar_buygoodsid=?";
		Object[] params={shopcarid,goodsid};
		mydb.doPstm(sql, params);
		try {
			ResultSet rs = mydb.getRs();
			if(rs!=null&&rs.next()){
				buynum=rs.getInt(1);
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return buynum;
	}
	public ShopcarBean getShopcar(String shopcarid){
		ShopcarBean shopcar=null;
		String sql="select * from tb_shopcar where shopcar_id=? and shopcar_buygoodscount!=0 order by id desc";
		Object[] params={shopcarid};
		mydb.doPstm(sql, params);
		try {
			ResultSet rs = mydb.getRs();
			if(rs!=null){
				shopcar=new ShopcarBean();
				GoodsDao goodsDao=new GoodsDao();
				shopcar.setShopcarId(shopcarid);
				while(rs.next())
					shopcar.setShopcarBuyGoodss(getBuyGoodsToShopcar(goodsDao,rs.getInt(3),rs.getInt(4)));
				goodsDao.closed();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return shopcar;
	}
	private GoodsBean getBuyGoodsToShopcar(GoodsDao goodsDao,int goodsbuyId,int goodsBuyNum) throws SQLException{
		GoodsBean single=goodsDao.getSingleGoods(goodsbuyId);
		if(single!=null)
			single.setGoodsBuyNum(goodsBuyNum);					//set goods number for purchase
		return single;
	}
	public boolean isBuy(Object[] params) throws SQLException{
		boolean mark=false;
		String sql="select id from tb_shopcar where shopcar_id=? and shopcar_buygoodsid=?";
		mydb.doPstm(sql, params);
		ResultSet rs=mydb.getRs();
		if(rs!=null&&rs.next()){
			mark=true;
			rs.close();
		}
		return mark;
	}
	public int addBuyNum(Object[] params){
		String sql="update tb_shopcar set shopcar_buygoodscount=shopcar_buygoodscount+1 where shopcar_id=? and shopcar_buygoodsid=?";
		return getUpdate(sql,params);
	}
	public int updateBuyNum(Object[] params){
		String sql="update tb_shopcar set shopcar_buygoodscount=? where shopcar_id=? and shopcar_buygoodsid=?";
		return getUpdate(sql,params);
	}
	public int addBuyGoods(Object[] params){
		String sql="insert into tb_shopcar values(?,?,?)";
		return getUpdate(sql,params);
	} 

	public int deleteGoods(String shopcarid,int goodsId){
		String sql="delete from tb_shopcar where shopcar_id=? and shopcar_buygoodsid=?";
		Object[] params={shopcarid,goodsId};
		return getUpdate(sql,params);
	}
	public int clearShopcar(String shopcarid){
		String sql="delete from tb_shopcar where shopcar_id=?";
		Object[] params={shopcarid};
		return getUpdate(sql,params);
	}
	private int getUpdate(String sql,Object[] params){
		mydb.doPstm(sql, params);
		int i=-1;
		try {
			i=mydb.getCount();
		} catch (SQLException e) {
			i=-1;
			e.printStackTrace();
		}
		return i;
	}
	public void closed(){
		mydb.closed();
	}	
}
