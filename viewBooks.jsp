<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%
   String path = request.getContextPath();
   String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Book Detail</title>
<link type="text/css" rel="stylesheet" href="Styles/site.css" />
</head>
<body>
<div class="page">
      <div class="header" style="position:relative; width:100%; background-color: rgba(75, 108, 158, 0.8);" align="center">
         <h1>
                    <span style="font-family: Arial; font-size: 1.1em;" >Welcome to Online Bookstore
                    </span>
         </h1>
      </div>
      
      <div class="main">
   <center>
   <div style="background:url(images/viewbk.jpg);width:720;height:583">
   <c:set var="single" value="${requestScope.goodsingle}"/> 
   <c:if test="${empty single}">Fail to get detail for the book.</c:if>
   <c:if test="${!empty single}">   
   <table border="0" width="50%" height="100%" cellspacing="3" cellpadding=3">  
      <tr>
         <td valign="middle" "50%" style="padding-top:173;padding-left:91"><img src="images/goods/${single.goodsViewpic}" width="220" height="180"></td>
         <td valign="bottom">
           Book name:&nbsp${single.goodsName}<br>
            Book price:&nbsp$${single.goodsPrice}<br>
            Book pages:&nbsp${single.goodsInfo}<br>      
            Author(s):&nbsp${single.goodsMaker}<br>
            <c:if test="${single.goodsStoreNum<=0}">
            <font color="red">Sorry, out of stock!</font><br/><a href="buy?buygoodsId=${single.id}">Add into wishlist</a></c:if>
            <c:if test="${single.goodsStoreNum>0}">
            <a href="buy?buygoodsId=${single.id}">Add into wishlist</a></c:if>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <a href="showwishlist">View wishlist</a>
         </td>
      </tr>
      <tr height="180" valign="top">
         <td colspan="2" style="padding-left:80">            
         </td>
      </tr>
   </table>
   </c:if>
   </div>
   </center>
   </div>
</div>
</body>
</html>