package com.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.toolsbean.PageBar;
import com.toolsbean.DB;

public class SuperDao {
	private PageBar daoPage=new PageBar();
	private int daoPerR=daoPage.getPerR();
	private int daoPerP=daoPage.getPerP();
	
	public void setDaoPerR(int daoPerR){
		this.daoPerR=daoPerR;
	}
	public void setDaoPerP(int daoPerP){
		this.daoPerP=daoPerP;
	}
	public void setDaoPage(String sql,Object[] params,String strcurrentP,String strcurrentG,String goWhich) throws SQLException{
		daoPage.setAllR(getAllR(sql,params));
		daoPage.setPerR(daoPerR);
		daoPage.setPerP(daoPerP);
		daoPage.setPageBar(strcurrentP, strcurrentG, goWhich);
	}
	private int getAllR(String sql,Object[] params) throws SQLException{
		int allR=0;
		DB mydb=new DB();
		mydb.doPstm(sql, params);
		ResultSet rs=mydb.getRs();
		if(rs!=null&&rs.next()){
			rs.last();
			allR=rs.getRow();
			rs.close();			
		}
		mydb.closed();
		return allR;		
	}
	public PageBar getDaoPage() {
		return daoPage;
	}
	public int getDaoPerR() {
		return daoPerR;
	}
	public int getDaoPerP() {
		return daoPerP;
	}
}
