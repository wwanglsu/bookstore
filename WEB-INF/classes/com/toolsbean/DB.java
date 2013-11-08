package com.toolsbean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DB {
	private Connection con;
	private PreparedStatement pstm;
	private String user="sa";
	private String password="sa!";
	private String className="com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private String url="jdbc:sqlserver://localhost:1433;DatabaseName=Bookstore"; 
	
	
	public DB(){
		try{
			Class.forName(className);
		}catch(ClassNotFoundException e){
			System.out.println("Fail to load Database Driver.");
			e.printStackTrace();
		}
	}

	
	public Connection getCon(){
		if(con==null){
			try {
				con=DriverManager.getConnection(url,user,password);
			} catch (SQLException e) {
				System.out.println("Fail to create database connection.");
				con=null;
				e.printStackTrace();
			}			
		}
		return con;
	}
	
	
	public void doPstm(String sql,Object[] params){
		if(sql!=null&&!sql.equals("")){
			if(params==null)
				params=new Object[0];			
			getCon();
			if(con!=null){
				try{		
					//System.out.println(sql);
					pstm=con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
					for(int i=0;i<params.length;i++){
						pstm.setObject(i+1,params[i]);
					}
					pstm.execute();
				}catch(SQLException e){
					System.out.println("doPstm() method error.");
					e.printStackTrace();
				}				
			}			
		}
	}
	
	
	public ResultSet getRs() throws SQLException{
		return pstm.getResultSet();		
	}
	
	public int getCount() throws SQLException{
		return pstm.getUpdateCount();		
	}
	
	public void closed(){
		try{
			if(pstm!=null)
				pstm.close();			
		}catch(SQLException e){
			System.out.println("Fail to close pstm object.");
			e.printStackTrace();
		}
		try{
			if(con!=null){
				con.close();
			}
		}catch(SQLException e){
			System.out.println("Fail to close connection object.");
			e.printStackTrace();
		}
	}
}
