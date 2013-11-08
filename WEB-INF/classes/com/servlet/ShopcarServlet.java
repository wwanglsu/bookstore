package com.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.GoodsDao;
import com.dao.ShopcarDao;
import com.dao.TempDao;
import com.toolsbean.StringHandler;
import com.valuebean.ShopcarBean;
import com.valuebean.UserBean;

public class ShopcarServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String servletpath=request.getServletPath();
		if("/buy".equals(servletpath))
			buy(request,response);
		else if("/showwishlist".equals(servletpath))
		   showwishlist(request,response);
		else if("/remove".equals(servletpath))
			remove(request,response);
		else if("/clearshopcar".equals(servletpath))
			clear(request,response);
		else if("/submitshopcar".equals(servletpath))
			submitDispatcher(request,response);
	}
	
	/***/
	protected void buy(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String message="";
		
		Integer buygoodsid=StringHandler.strToint(request.getParameter("buygoodsId"));
		System.out.println(buygoodsid);
		if(buygoodsid==null){
			message="<li>Book ID Error!</li>";
			message+="<a href='window.history.go(-1)'>Back</a>";
		}
		else{	
			try {					
				Date now=new Date();										//get current time
				TempDao tempDao=new TempDao();
				String shopcarid=seeshopcarcookie(request,response);		//check the cookie in client to whether contain a wishlist id

				if(shopcarid==null||shopcarid.equals("")||!tempDao.isexist(shopcarid)){					//no save
					shopcarid=addshopcarcookie(request,response,now);								//get a wishlist id and save into client cookie, return the id value.
					tempDao.saveShopcarCreateTime(shopcarid, StringHandler.timeTostr(now));			//record the wishlist id and create time in table
				}
				
				int i=-1;
				ShopcarDao shopcarDao=new ShopcarDao();
				Object[] params={shopcarid,buygoodsid};
				if(shopcarDao.isBuy(params)){				//if already bought the book
					i=shopcarDao.addBuyNum(params);			//update the book quantity
				}	
				else{										//haven't bought the book.
					params=new Object[]{shopcarid,buygoodsid,1};
					i=shopcarDao.addBuyGoods(params);					//add the book into wishlist	
				}
				shopcarDao.closed();
				
				if(i<=0)
					message="<li>Fail to add into wishlist.</li><br>";
				else
					message="<li>Suceed to add into wishlist.</li><br>";
				message+="<a href='javascript:window.history.go(-1)'>Back</a>";
				message+="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
				message+="<a href='showwishlist'>View wishlist<a/>";
				message+="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
				message+="<a href=\"javascript:window.close()\">Close Window</a>";
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}			
		
		request.setAttribute("message",message);
		RequestDispatcher rd=request.getRequestDispatcher("/message.jsp");
		rd.forward(request,response);	
	}
	
	private String seeshopcarcookie(HttpServletRequest request,HttpServletResponse response){
		String webname=request.getContextPath();
		webname=webname.substring(1);
		
		Cookie[] coks=request.getCookies();
		String shopcarid="";
		
		int i=0;
		for(;i<coks.length;i++){
			Cookie icok=coks[i];
			if(icok.getName().equals(webname+".usershopcar")){
				shopcarid=icok.getValue();
				break;
			}
		}	
		return shopcarid;
	}
	
	private String addshopcarcookie(HttpServletRequest request,HttpServletResponse response,Date date){
		String webname=request.getContextPath();
		webname=webname.substring(1);
		String shopcarid="car"+StringHandler.getSerial(date);
		Cookie shopcar=new Cookie(webname+".usershopcar",shopcarid);
		shopcar.setPath("/");
		int maxage=60*60*24*300;				//Set cookie expired in 300 days		
		shopcar.setMaxAge(maxage);
		response.addCookie(shopcar);
		return shopcarid;
	}
	protected void showwishlist(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String shopcarid=seeshopcarcookie(request,response);
		if(shopcarid!=null&&!shopcarid.equals("")){
			ShopcarBean shopcar=new ShopcarDao().getShopcar(shopcarid);
			request.setAttribute("shopcar",shopcar);
		}
		RequestDispatcher rd=request.getRequestDispatcher("/showWishlist.jsp");
		rd.forward(request,response);
	}
	protected void remove(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		Integer goodsId=StringHandler.strToint(request.getParameter("goodsId"));
		String shopcarid=seeshopcarcookie(request,response);
		
		if(shopcarid!=null&&!shopcarid.equals("")&&goodsId!=null){
			ShopcarDao shopcarDao=new ShopcarDao();
			int i=shopcarDao.deleteGoods(shopcarid,goodsId);
			if(i<=0)
				request.setAttribute("message","Fail to remove the book!");
			else
				request.setAttribute("message","Succeed to remove the book.");
			shopcarDao.closed();
		}
		showwishlist(request,response);
	}
	protected void clear(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String shopcarid=seeshopcarcookie(request,response);
		if(shopcarid!=null&&!shopcarid.equals("")){
			ShopcarDao shopcarDao=new ShopcarDao();
			int i=shopcarDao.clearShopcar(shopcarid);
			if(i<=0)
				request.setAttribute("message","Fail to clear the wishlist!");
			else
				request.setAttribute("message","Succeed to clear the wishlist.");
			shopcarDao.closed();
		}
		RequestDispatcher rd=request.getRequestDispatcher("/showWishlist.jsp");
		rd.forward(request,response);
	}
	
	protected void submitDispatcher(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		String whichsubmit=request.getParameter("whichsubmit");
		if(whichsubmit==null) ;
//			payforMoney(request,response);
		else
			updatebuyNum(request,response);		
	}	
	
	
	protected void updatebuyNum(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		shopcar_validateBuyNum(request, response);
		showwishlist(request,response);
	}
	
	private boolean shopcar_validateBuyNum(HttpServletRequest request,HttpServletResponse response){
		boolean mark=true;
		String[] goodsStoreNums=request.getParameterValues("buygoodsstorenum");
		String[] buyNums=request.getParameterValues("buygoodsnum");
		String[] goodsIds=request.getParameterValues("buygoodsids");
		String shopcarid=seeshopcarcookie(request,response);
		
		if(goodsIds!=null&&goodsIds.length!=0&&shopcarid!=null){
			Map messages=new HashMap();
			Object[] params=new Object[3];
			ShopcarDao shopcarDao=new ShopcarDao();
			for(int i=0;i<goodsIds.length;i++){
				int int_buyNum=Integer.parseInt(buyNums[i]);
				int int_goodsStoreNums=Integer.parseInt(goodsStoreNums[i]); 
				
				if(int_buyNum>int_goodsStoreNums){
					mark=false;
					messages.put(i,"Out of stock!");
				}
				else if(int_buyNum<=0)
					shopcarDao.deleteGoods(shopcarid, Integer.parseInt(goodsIds[i]));
				else{
					params[0]=int_buyNum;
					params[1]=shopcarid;
					params[2]=goodsIds[i];
					shopcarDao.updateBuyNum(params);
					messages.put(i,"Updated.");
				}
			}
			request.setAttribute("messages",messages);
			shopcarDao.closed();
		}
		else
			mark=false;
		return mark;
	}

	private void deleteshopcarcookie(HttpServletRequest request,HttpServletResponse response){
		String webname=request.getContextPath();
		webname=webname.substring(1);
		Cookie shopcar=new Cookie(webname+".usershopcar",null);
		shopcar.setPath("/");
		int maxage=0;		
		shopcar.setMaxAge(maxage);
		response.addCookie(shopcar);
	}
}