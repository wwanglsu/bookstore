<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%
   String path = request.getContextPath();
   String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>My Wishlist</title>
	 <link type="text/css" rel="stylesheet" href="Styles/site.css" />
  </head>
  <body>
  <div class="page">
      <div class="header" style="position:relative; width:100%; background-color: rgba(75, 108, 158, 0.8);" align="center">
         <h1>
                    <span style="font-family: Arial; font-size: 1.1em;" >Welcome to Online Bookstore</span>
         </h1>
      </div>      
      <div class="main" align="center">
      
         <table>       
		      <tr height="54">
		         <td colspan="6" style="padding-left:20">
		            Welcome to wishlist. <br/><font color="red">${requestScope.message}</font>
		         </td>
		      </tr>
		      <tr height="40" style="font-size:12">
		         <th width="10%">Order</th>
		         <th align="left">Book Name</th>
		         <th align="center" width="15%">Status</th>
		         <th width="15%">Price</th>
		         <th width="15%">Quantity</th>
		         <th width="15%">Amount</th>
		         <th width="10%">Remove</th>
		      </tr>
		      <c:set var="myshopcar" value="${requestScope.shopcar}"/>
		      <c:if test="${(empty myshopcar) or (empty myshopcar.shopcarBuyGoodss)}">
		      <tr height="80"><td colspan="6" align="center">You haven't add any book into your wishlist.</td></tr>
		      </c:if>
		      <c:if test="${(!empty myshopcar) and (!empty myshopcar.shopcarBuyGoodss)}">
		      <form action="submitshopcar" name="updateform" method="post">
		      <c:forEach var="buygoods" varStatus="bvs" items="${myshopcar.shopcarBuyGoodss}">
		         <c:if test="${!empty buygoods}">
		      <input type="hidden" name="buygoodsids" value="${buygoods.id}">
		      <input type="hidden" name="buygoodsstorenum" value="${buygoods.goodsStoreNum}">
		      <tr height="30">
		         <td align="center">${bvs.count}</td>
		         <td><a href="viewbooks?goodId=${buygoods.id}" target="_blank">${buygoods.goodsName}</a></td>
		         <td align="center">${(buygoods.goodsStoreNum)==0 ? "Out of stock.":"In stock."}</td>
		         <td align="center">$${buygoods.goodsPrice}</td>
		         <td align="center"><input type="text" name="buygoodsnum" value="${buygoods.goodsBuyNum}" size="6" style="text-align:center;border:1 solid"><br><font color="red">${requestScope.messages[bvs.index]}</font></td>
		         <td align="center">$${buygoods.goodsMoney}</td>
		         <td align="center"><a href="remove?goodsId=${buygoods.id}">Remove</a></td>
		      </tr>
		      <c:set var="totalmoney" value="${requestScope.totalmoney+buygoods.goodsMoney}" scope="request"/>
		         </c:if>
		      </c:forEach>
		      <input type="hidden" name="goodsprices" value="${totalmoney}">
		      <tr height="40"><td colspan="6"><hr align="right" width="100%" color="black"></td></tr>
		      <tr height="20"><td colspan="6" style="padding-left:30">Total amount:&nbsp<input type="text" name="goodsprices" value="<%="$"+String.format("%.2f",request.getAttribute("totalmoney"))%>" style="border:0" disabled></td></tr>
		      <tr height="50" valign="bottom">
		         <td colspan="3" style="padding-left:30">
		            <input type="submit" name="whichsubmit" value="Update Quantity">
		         </td>
		         <td colspan="5" align="right"><a href="clearshopcar">Clear Wishlist</a></td>
		      </tr>
		      </form>
		      </c:if>
	      <tr height="40"><td colspan="6" align="center"><a href="javascript:window.close()">Close Window</a></td></tr>
	    </table>
	    <table border="0" cellpadding="0" cellspacing="0">
	      <tr><td></td></tr>
	   </table>
    
    </div>
  </div>
  </body>
</html>
