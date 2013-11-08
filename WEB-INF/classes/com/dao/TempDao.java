package com.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.toolsbean.DB;

public class TempDao {
	public boolean isexist(String shopcarid) throws SQLException{
		boolean mark=false;
		String sql="select id from tb_shopcar where shopcar_id=?";
		Object[] params={shopcarid};
		DB mydb=new DB();
		mydb.doPstm(sql, params);
		ResultSet rs=mydb.getRs();
		if(rs!=null&&rs.next()){
			mark=true;
			rs.close();
		}
		mydb.closed();
		return mark;
	}
	public void saveShopcarCreateTime(String shopcarid,String date){
		String sql="insert into tb_temp values(?,?)";
		Object[] params={shopcarid,date};
		getUpdate(sql,params);
	}
	public void deleteShopcar(String shopcarid){
		String sql="delete from tb_temp where shopcar_id=?";
		Object[] params={shopcarid};
		getUpdate(sql,params);
	}
	private void getUpdate(String sql,Object[] params){
		DB mydb=new DB();
		mydb.doPstm(sql, params);
		mydb.closed();
	}
}
