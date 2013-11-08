package com.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.toolsbean.DB;
import com.valuebean.UserBean;

public class UserDao {
	public UserBean login(String name,String pswd){
		UserBean loginer=null;
		String sql="select * from tb_user where user_name=? and user_pswd=?";
		Object[] params={name,pswd};
		DB mydb=new DB();
		mydb.doPstm(sql, params);
		try {
			ResultSet rs=mydb.getRs();
			if(rs!=null&&rs.next()){
				loginer=new UserBean();
				loginer.setId(rs.getInt(1));
				loginer.setUserName(rs.getString(2));
				loginer.setUserPswd(rs.getString(2));
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		mydb.closed();
		return loginer;
	}
}
