package com.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
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
import com.toolsbean.StringHandler;
import com.valuebean.GoodsBean;

public class GoodsServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String servletpath=request.getServletPath();
		if("/listbrowsebooks".equals(servletpath))
			listbrowsebooks(request,response);
		else if("/listbooks".equals(servletpath))
			listbooks(request,response);
		else if("/viewbooks".equals(servletpath))
		   viewbooks(request,response);
		else if("/clearbrowse".equals(servletpath))
			clearbrowse(request,response);
	}
	protected void listbrowsebooks(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		Map ckmap=getcookie(request);
		if(ckmap.size()==4){
			List browsegoodslist=new ArrayList();
			int[] ids=StringHandler.changeToIntArray(((String)ckmap.get("ckid")).split("I"));
			String[] names=((String)ckmap.get("ckname")).split("I");
			String[] viewpics=((String)ckmap.get("ckviewpic")).split("I");
			String[] times=((String)ckmap.get("cktime")).split("I");
			
			if((ids.length==names.length)&&(ids.length==viewpics.length)&&(ids.length==times.length)){
				for(int i=0;i<ids.length;i++){
					Map browsesingle=new HashMap();
					browsesingle.put("browseid",ids[i]);
					browsesingle.put("browsename",names[i]);
					browsesingle.put("browseviewpic",viewpics[i]);
					browsesingle.put("browsetime",times[i]);
					
					browsegoodslist.add(browsesingle);
				}
				request.setAttribute("browsegoodslist", browsegoodslist);
			}			
		}
		
		RequestDispatcher rd=request.getRequestDispatcher("listBrowseBooks.jsp");
		rd.forward(request,response);
	}
	private Map getcookie(HttpServletRequest request){
		Map ckmap=new HashMap();
		String webname=request.getContextPath();
		webname=webname.substring(1);		
		Cookie[] coks=request.getCookies();	
		
		int i=0;
		for(i=0;i<coks.length;i++){
			Cookie icok=coks[i];
			if(icok.getName().equals(webname+".browsegoodsid"))
				ckmap.put("ckid",icok.getValue());
			else if(icok.getName().equals(webname+".browsegoodsname"))
				ckmap.put("ckname",StringHandler.cookieDecCode(icok.getValue()));
			else if(icok.getName().equals(webname+".browsegoodsviewpic"))
				ckmap.put("ckviewpic",icok.getValue());	
			else if(icok.getName().equals(webname+".browsegoodstime"))
				ckmap.put("cktime",icok.getValue());	
			
			if(ckmap.size()==4)
				break;
		}
		return ckmap;
	}
	protected void listbooks(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		try {
			GoodsDao goodsDao=new GoodsDao();
			String strcurrentP=request.getParameter("currentP");
			String strcurrentG=request.getParameter("currentG");
			String goWhich="listbooks";
			List goodslist=goodsDao.getAllGoods(strcurrentP,strcurrentG,goWhich);
			
			request.setAttribute("goodslist",goodslist);
			request.setAttribute("pageBar",goodsDao.getDaoPage());
			
			goodsDao.closed();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		RequestDispatcher rd=request.getRequestDispatcher("/listBooks.jsp");
		rd.forward(request,response);
	}
	protected void viewbooks(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		Integer goodId=StringHandler.strToint(request.getParameter("goodId"));
		if(goodId!=null){
			try {
				GoodsDao goodsDao=new GoodsDao();
				GoodsBean single=new GoodsDao().getSingleGoods(goodId);
				goodsDao.closed();
				if(single!=null){
					seebrowsegoodscookie(request,response,single);
					request.setAttribute("goodsingle",single);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		RequestDispatcher rd=request.getRequestDispatcher("/viewBooks.jsp");
		rd.forward(request,response);
	}
	private void seebrowsegoodscookie(HttpServletRequest request,HttpServletResponse response,GoodsBean single)throws ServletException, IOException {
		String webname=request.getContextPath();
		webname=webname.substring(1);
		String now=StringHandler.timeTostr(new Date());
		int goodId=single.getId();
		String goodName=single.getGoodsName();
		String goodViewpic=single.getGoodsViewpic();
		
		Cookie[] coks=request.getCookies();
		Cookie browsegoodsid=null;
		Cookie browsegoodsname=null;
		Cookie browsegoodsviewpic=null;
		Cookie browsegoodstime=null;
		
		String ckid="";
		String ckname="";
		String ckviewpic="";
		String cktime="";

		int i=0;
		for(i=0;i<coks.length;i++){
			Cookie icok=coks[i];
			if(icok.getName().equals(webname+".browsegoodsid"))
				browsegoodsid=icok;
			else if(icok.getName().equals(webname+".browsegoodsname"))
				browsegoodsname=icok;
			else if(icok.getName().equals(webname+".browsegoodsviewpic"))
				browsegoodsviewpic=icok;			
			else if(icok.getName().equals(webname+".browsegoodstime"))
				browsegoodstime=icok;			
			if(browsegoodsid!=null&&browsegoodsname!=null&&browsegoodsviewpic!=null&&browsegoodstime!=null)
				break;
		}
		boolean mark=false;
		if(i<coks.length){
			ckid=browsegoodsid.getValue();
			ckname=browsegoodsname.getValue();
			ckviewpic=browsegoodsviewpic.getValue();
			cktime=browsegoodstime.getValue();
			
			String idregex="^"+goodId+"I\\S*$|^\\S*I"+goodId+"I\\S*$|^\\S*I"+goodId+"$|^"+goodId+"$";
			if(!ckid.matches(idregex)){
				ckid=goodId+"I"+ckid;
				ckname=goodName+"I"+StringHandler.cookieDecCode(ckname);
				ckviewpic=goodViewpic+"I"+ckviewpic;
				cktime=now+"I"+cktime;
				mark=true;
			}
		}			
		else{
			ckid=goodId+"";
			ckname=goodName;
			ckviewpic=goodViewpic;
			cktime=now;
			mark=true;
		}
		
		if(mark){
			browsegoodsid=new Cookie(webname+".browsegoodsid",ckid);
			browsegoodsname=new Cookie(webname+".browsegoodsname",StringHandler.cookieEnCode(ckname));
			browsegoodsviewpic=new Cookie(webname+".browsegoodsviewpic",ckviewpic);
			browsegoodstime=new Cookie(webname+".browsegoodstime",cktime);
			
			addcookie(browsegoodsid,response);
			addcookie(browsegoodsname,response);
			addcookie(browsegoodsviewpic,response);
			addcookie(browsegoodstime,response);
		}
	}
	private void addcookie(Cookie cookie,HttpServletResponse response){
		cookie.setPath("/");
		int maxage=60*60*24*300;				//set the cookie available day=300		
		cookie.setMaxAge(maxage);
		response.addCookie(cookie);		
	}
	protected void clearbrowse(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String webname=request.getContextPath();
		webname=webname.substring(1);
		
		Cookie clearbrowsegoodsid=new Cookie(webname+".browsegoodsid",null);
		Cookie clearbrowsegoodsname=new Cookie(webname+".browsegoodsname",null);
		Cookie clearbrowsegoodsviewpic=new Cookie(webname+".browsegoodsviewpic",null);
		Cookie clearbrowsegoodstime=new Cookie(webname+".browsegoodstime",null);

		delbrowsegoodscookie(clearbrowsegoodsid,response);
		delbrowsegoodscookie(clearbrowsegoodsname,response);
		delbrowsegoodscookie(clearbrowsegoodsviewpic,response);
		delbrowsegoodscookie(clearbrowsegoodstime,response);
		
		RequestDispatcher rd=request.getRequestDispatcher("/listBrowseBooks.jsp");
		rd.forward(request,response);
	}
	private void delbrowsegoodscookie(Cookie cookie,HttpServletResponse response){
		cookie.setPath("/");
		cookie.setMaxAge(0);
		response.addCookie(cookie);		
	}
}